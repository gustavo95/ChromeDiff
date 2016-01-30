
public class Structure {
	private String structure;
	private String file;
	private String commit;
	private String cve;
	
	public Structure() {
		this.structure = "";
		this.file = "";
		this.commit = "";
		this.cve = "";
	}
	
	public Structure(String structure, String file, String commit, String cve) {
		this.structure = structure;
		this.file = file;
		this.commit = commit;
		this.cve = cve;
	}

	public String getStructure() {
		return structure;
	}

	public void setStructure(String structure) {
		this.structure = structure;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getCommit() {
		return commit;
	}

	public void setCommit(String commit) {
		this.commit = commit;
	}

	public String getCve() {
		return cve;
	}

	public void setCve(String cve) {
		this.cve = cve;
	}
}
