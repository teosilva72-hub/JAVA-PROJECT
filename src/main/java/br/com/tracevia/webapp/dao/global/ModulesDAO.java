package br.com.tracevia.webapp.dao.global;

import java.util.ArrayList;

import br.com.tracevia.webapp.model.global.Modules;
import br.com.tracevia.webapp.model.global.SQL_Tracevia;
import br.com.tracevia.webapp.model.global.ColumnsSql.RowResult;
import br.com.tracevia.webapp.model.global.ResultSql.MapResult;

public class ModulesDAO {

	SQL_Tracevia conn;

	public ModulesDAO() throws Exception {

		try {
			conn = new SQL_Tracevia();
		} catch (Exception e) {

			throw new Exception("erro: \n" + e.getMessage());
		}
	}

	/**
	 * M�todo para criado para obter uma lista com m�dulos ativos no sistema.
	 * 
	 * @return uma lista com m�dulos ativos
	 * @throws Exception
	 * @see list
	 **/

	public ArrayList<Modules> listModules() throws Exception {

		ArrayList<Modules> modules = new ArrayList<Modules>();

		String query = null;

		try {
			conn.start(1);

			query = "SELECT module, battery_voltage, enabled FROM tracevia_core.modules WHERE enabled = 1";

			conn.prepare(query);
			MapResult result = conn.executeQuery();

			 System.out.println(result.first());

			if (result.hasNext()) {
				for (RowResult rs : result) {

					Modules mod = new Modules();

					mod.setModule(rs.getString("module"));
					mod.setBattery_voltage(rs.getDouble("battery_voltage"));
					mod.setEnabled(rs.getBoolean("enabled"));
					modules.add(mod);

				}

			}

		} catch (Exception e) {

			e.printStackTrace();

		} finally {
			conn.close();
		}

		return modules;
	}

	// Pull Credentials
	public String[] getCred(String name) throws Exception {
		String[] credentials = new String[6];
		String query;

		try {
			conn.start(1);

			query = "SELECT name, user, pass, address, port, ws FROM tracevia_core.credentials WHERE name = ?";

			conn.prepare(query);
			conn.setString(1, name);

			MapResult result = conn.executeQuery();

			// System.out.println(query);

			if (result.hasNext()) {
				RowResult rs = result.first();

				credentials[0] = rs.getString("name");
				credentials[1] = rs.getString("user");
				credentials[2] = rs.getString("pass");
				credentials[3] = rs.getString("address");
				credentials[4] = rs.getString("port");
				credentials[5] = rs.getString("ws");
			}

		} catch (Exception e) {

			e.printStackTrace();

		} finally {
			conn.close();
		}

		return credentials;
	}
}
