package itplus.buoi08.sqlite;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import com.example.itplus.buoi08.sqlite.R;

public class MainActivity extends ListActivity {
	private GhiChuDataSource datasource;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_main);

		datasource = new GhiChuDataSource(this);
		datasource.open();

		List<GhiChuEntity> values = datasource.getAllGhiChu();

		// use the SimpleCursorAdapter to show the
		// elements in a ListView
		ArrayAdapter<GhiChuEntity> adapter = new ArrayAdapter<GhiChuEntity>(this,
				android.R.layout.simple_list_item_1, values);
		setListAdapter(adapter);
	}

	// Hàm được gọi thông qua thuộc tính onClick viết ở trên button trong file
	// layout_main.xml
	public void onClick(View view) {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		String strFakeData = dateFormat.format(cal.getTime());

		@SuppressWarnings("unchecked")
		ArrayAdapter<GhiChuEntity> adapter = (ArrayAdapter<GhiChuEntity>) getListAdapter();
		GhiChuEntity ghiChuEntity = null;
		switch (view.getId()) {
		case R.id.add:
			// save the new comment to the database
			ghiChuEntity = datasource.createGhiChu(strFakeData);
			adapter.add(ghiChuEntity);
			break;
		case R.id.update:
			if (getListAdapter().getCount() > 0) {
				ghiChuEntity = (GhiChuEntity) getListAdapter().getItem(0);
				ghiChuEntity.setComment(strFakeData);
				datasource.updateGhiChu(ghiChuEntity);
				adapter.notifyDataSetChanged();
			}
			break;
		case R.id.delete:
			if (getListAdapter().getCount() > 0) {
				ghiChuEntity = (GhiChuEntity) getListAdapter().getItem(0);
				datasource.deleteGhiChu(ghiChuEntity);
				adapter.remove(ghiChuEntity);
			}
			break;
		}
		adapter.notifyDataSetChanged();
	}

	@Override
	protected void onResume() {
		// Khi activity khởi chạy sẽ mở database để bắt đầu thao tác với dữ liệu
		datasource.open();
		super.onResume();
	}

	@Override
	protected void onPause() {
		// Trước khi đóng activity thì database sẽ được đóng trước
		datasource.close();
		super.onPause();
	}

}