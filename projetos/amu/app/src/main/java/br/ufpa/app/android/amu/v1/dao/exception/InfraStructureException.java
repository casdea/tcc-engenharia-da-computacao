package br.ufpa.app.android.amu.v1.dao.exception;

public final class InfraStructureException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 8372746578765672042L;
	private int code;

    public InfraStructureException(int code, String e) {
        super(e);
        this.code = code;
    }

    public InfraStructureException(int code) {
        this.code = code;
    }

    public InfraStructureException(Exception e) {
        super(e);
    }

	public int getCode() {
		return code;
	}
}
