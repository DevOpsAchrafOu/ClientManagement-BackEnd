/**
 * 
 */
package ma.mang.be.api.dto;

/**
 * @author achraf
 * @version v1.0
 * 
 */
public class PasswordDto {
	
	private String oldPwd;
	private String newPwd;
	public String getOldPwd() {
		return oldPwd;
	}
	public void setOldPwd(String oldPwd) {
		this.oldPwd = oldPwd;
	}
	public String getNewPwd() {
		return newPwd;
	}
	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}
	
	

}
