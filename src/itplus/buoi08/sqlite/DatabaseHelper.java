/**
 * 
 */
package itplus.buoi08.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * @author VTMinh
 * 
 */
public class DatabaseHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "dbNoteManager.sqlite"; // Tên CSDL
	private static final int DATABASE_VERSION = 1; // Phiên bản CSDL (version)

	// Tên bảng
	public static final String TABLE_GHICHU = "tblGhiChu";
	// Tên cột
	public static final String GHICHU_ID = "_id";
	// Tên cột
	public static final String COLUMN_GHICHU = "ghichu";

	// Câu lệnh SQL khởi tạo bảng tblGhiChu trong Database
	private static final String DATABASE_CREATE = "create table "
			+ TABLE_GHICHU + "(" + GHICHU_ID
			+ " integer primary key autoincrement, " + COLUMN_GHICHU
			+ " text not null);";

	public DatabaseHelper(Context context) {
		// Khi khởi tạo đối tượng DatabaseHelper thì hàm super cần truyền vào
		// tên Database và số hiệu phiên bản database. Trong trường hợp số hiệu
		// phiên bản thay đổi thì hàm onUpgrade sẽ được gọi
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Nó sẽ được gọi lần đầu tiên khi bạn cài ứng dụng
	// Hoặc khi mà ứng dụng của bạn bị người dùng xóa CSDL và sau đó ứng dụng được khởi động lại thì 
	// CSDL sẽ tạo mới hoàn toàn
	@Override
	public void onCreate(SQLiteDatabase database) {
		// Khởi tạo database lần đầu tiên
		database.execSQL(DATABASE_CREATE);
	}

	// Khi phiên bản ứng dụng nâng cấp, database thay đổi thêm trường hoặc bớt
	// trường dữ liệu thì cần viết lệnh cập nhật theo phiên bản ở đây
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.e(DatabaseHelper.class.getName(), "Cập nhật CSDL từ phiên bản "
				+ oldVersion + " đến " + newVersion
				+ ", vui lòng kiểm tra cẩn thẩn để tránh tổn thất dữ liệu cũ");

		// Viết câu lệnh cập nhật database ở đây - ví dụ tạo 1 bảng mới
		db.execSQL("CREATE TABLE \"tblUser\" (\"id\" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL, \"name\" TEXT, \"type\" TEXT)");
	}

}