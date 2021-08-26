package br.com.tracevia.webapp.model.global;

/**
 * Classe Padrão Builder para confecionar modelos de Queries
 * @author Wellington 20-08-2021
 *
 */

public class SQLBuilder {

	  private String queryHeader;
	  private String queryMain;
	  private String queryTable;
	  private String queryIndex;
	  private String queryWhere;
	  private String queryJoin;
	  private String queryGroupBy;
	  private String queryOrderBy;

	public static class Builder {

		private String queryHeader;
		private String queryMain;
		private String queryTable;
		private String queryIndex;
		private String queryJoin;
		private String queryWhere;		
		private String queryGroupBy;
		private String queryOrderBy;

		// QUERY HEADER PART
		public Builder queryHeader(String queryHeader) {
			this.queryHeader = queryHeader;			
			return this;			
		}

		// QUERY MAIN PART
		public Builder queryMain(String queryMain) {
			this.queryMain = queryMain;			
			return this;			
		}

		// QUERY TABLE PART
		public Builder queryTable(String queryTable) {
			this.queryTable = queryTable;			
			return this;			
		}

		// QUERY INDEX PART
		public Builder queryIndex(String queryIndex) {
			this.queryIndex = queryIndex;			
			return this;			
		}

		// QUERY JOIN PART
		public Builder queryJoin(String queryJoin) {
			this.queryJoin = queryJoin;			
			return this;			
		}

		// QUERY WHERE PART
		public Builder queryWhere(String queryWhere) {
			this.queryWhere = queryWhere;			
			return this;			
		}

		// QUERY WHERE PART
		public Builder queryGroupBy(String queryGroupBy) {
			this.queryGroupBy = queryGroupBy;			
			return this;			
		}

		// QUERY ORDER BT PART
		public Builder queryOrderBy(String queryOrderBy) {
			this.queryOrderBy = queryOrderBy;			
			return this;			
		}

		// GETTERS NECESSARY

		public String getQueryHeader() {
			return queryHeader;
		}

		public String getQueryMain() {
			return queryMain;
		}

		public String getQueryTable() {
			return queryTable;
		}

		public String getQueryIndex() {
			return queryIndex;
		}

		public String getQueryJoin() {
			return queryJoin;
		}

		public String getQueryWhere() {
			return queryWhere;
		}

		public String getQueryGroupBy() {
			return queryGroupBy;
		}

		public String getQueryOrderBy() {
			return queryOrderBy;
		}					

	}

	public String getQueryHeader() {
		return queryHeader;
	}

	// GETTERS AND SETTERS

	public void setQueryHeader(String queryHeader) {
		this.queryHeader = queryHeader;
	}

	public String getQueryMain() {
		return queryMain;
	}

	public void setQueryMain(String queryMain) {
		this.queryMain = queryMain;
	}

	public String getQueryTable() {
		return queryTable;
	}

	public void setQueryTable(String queryTable) {
		this.queryTable = queryTable;
	}

	public String getQueryIndex() {
		return queryIndex;
	}

	public void setQueryIndex(String queryIndex) {
		this.queryIndex = queryIndex;
	}

	public String getQueryWhere() {
		return queryWhere;
	}

	public void setQueryWhere(String queryWhere) {
		this.queryWhere = queryWhere;
	}

	public String getQueryJoin() {
		return queryJoin;
	}

	public void setQueryJoin(String queryJoin) {
		this.queryJoin = queryJoin;
	}

	public String getQueryGroupBy() {
		return queryGroupBy;
	}

	public void setQueryGroupBy(String queryGroupBy) {
		this.queryGroupBy = queryGroupBy;
	}

	public String getQueryOrderBy() {
		return queryOrderBy;
	}

	public void setQueryOrderBy(String queryOrderBy) {
		this.queryOrderBy = queryOrderBy;
	}

}
