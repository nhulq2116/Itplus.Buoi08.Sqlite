/**
 * 
 */
package itplus.buoi08.sqlite;

/**
 * @author VTMinh
 *
 */
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Xử lý dữ liệu với bảng ghichu thông qua đối tượng SQLiteDatabase
 * 
 */
public class GhiChuDataSource {

	// Database fields
	private SQLiteDatabase database;
	private DatabaseHelper dbHelper;
	private String[] allColumns = { DatabaseHelper.GHICHU_ID,
			DatabaseHelper.COLUMN_GHICHU };

	// Hàm contructor kiêm nhiệm vụ khởi tạo Database Helper
	public GhiChuDataSource(Context context) {
		dbHelper = new DatabaseHelper(context);
	}

	// Hàm xin phép mở database để đọc ghi dữ liệu
	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	// Hàm đóng database lại kết thúc việc đọc ghi dữ liệu
	public void close() {
		dbHelper.close();
	}

	// Thêm một bản ghi
	public GhiChuEntity createGhiChu(String comment) {
		// Ghi dữ liệu xuống database
		ContentValues values = new ContentValues();
		values.put(DatabaseHelper.COLUMN_GHICHU, comment);
		long insertId = database.insert(DatabaseHelper.TABLE_GHICHU, null,
				values);

		// Trỏ tới database lấy dữ liệu vừa lưu - để kiểm tra xem dữ liệu ghi đúng chưa
		// Truy vấn lấy bản ghi có ID = insertId
		Cursor cursor = database.query(DatabaseHelper.TABLE_GHICHU,
				allColumns, DatabaseHelper.GHICHU_ID + " = " + insertId, null,
				null, null, null);
		cursor.moveToFirst();
		GhiChuEntity newComment = cursorToComment(cursor);
		cursor.close();

		// Trả về đối tượng Comment
		return newComment;
	}

	// Cập nhật một bản ghi
	public void updateGhiChu(GhiChuEntity ghiChuEntity) {
		ContentValues values = new ContentValues();
		values.put(DatabaseHelper.COLUMN_GHICHU, ghiChuEntity.getComment());

		// Điều kiện
		String whereClause = DatabaseHelper.GHICHU_ID + " = ?";
		// Tham số cho điều kiện
		String[] whereArgs = new String[] { String.valueOf(ghiChuEntity.getId()) };
		// Thực hiện câu lệnh
		database.update(DatabaseHelper.TABLE_GHICHU, values, whereClause,
				whereArgs);

		long id = ghiChuEntity.getId();
		Log.e("CommentsDataSource", "Comment update with id: " + id);		
	}

	// Xóa một bản ghi
	public void deleteGhiChu(GhiChuEntity ghiChuEntity) {
		long id = ghiChuEntity.getId();
		Log.e("CommentsDataSource", "Comment deleted with id: " + id);
		database.delete(DatabaseHelper.TABLE_GHICHU, DatabaseHelper.GHICHU_ID
				+ " = " + id, null);
	}

	// Lấy tất cả dữ liệu
	public List<GhiChuEntity> getAllGhiChu() {
		List<GhiChuEntity> ghiChuEntities = new ArrayList<GhiChuEntity>();

		Cursor cursor = database.query(DatabaseHelper.TABLE_GHICHU,
				allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			GhiChuEntity ghiChuEntity = cursorToComment(cursor);
			ghiChuEntities.add(ghiChuEntity);
			cursor.moveToNext();
		}
		// Đóng cursor lại
		cursor.close();
		return ghiChuEntities;
	}

	private GhiChuEntity cursorToComment(Cursor cursor) {
		GhiChuEntity ghiChuEntity = new GhiChuEntity();
		ghiChuEntity.setId(cursor.getLong(0));
		ghiChuEntity.setComment(cursor.getString(1));
		return ghiChuEntity;
	}
}
