/**
 * 
 */
package itplus.buoi08.sqlite;

/**
 * @author VTMinh Class đối tượng Ghi Chú
 */
public class GhiChuEntity {
	private long id;
	private String ghichu;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getComment() {
		return ghichu;
	}

	public void setComment(String comment) {
		this.ghichu = comment;
	}

	// Will be used by the ArrayAdapter in the ListView
	@Override
	public String toString() {
		return ghichu;
	}
}
