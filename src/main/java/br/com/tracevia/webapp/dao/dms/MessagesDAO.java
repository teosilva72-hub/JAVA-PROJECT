
package br.com.tracevia.webapp.dao.dms;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import br.com.tracevia.webapp.methods.DateTimeApplication;
import br.com.tracevia.webapp.model.dms.Messages;
import br.com.tracevia.webapp.util.ConnectionFactory;

public class MessagesDAO {
	
	private Connection conn;
	protected ConnectionFactory connection = new ConnectionFactory();
	private PreparedStatement ps;
	private ResultSet rs;
	
	//LanguageMB lang;
	Locale locale;
	ResourceBundle resourceBundle;

	public MessagesDAO() throws Exception {

		try {
			this.conn = ConnectionFactory.getConnection();	
		} catch (Exception e) {
			throw new Exception("erro: \n" + e.getMessage());
		}
	}
		
	 public List<Messages> mensagensDisponiveis() throws Exception {
		  
	     List<Messages> lista = new ArrayList<Messages>();
	  
	  try {
	  
		conn = ConnectionFactory.connectToTraceviaApp(); 
		ps = conn.prepareStatement("SELECT id_reg, type, name, id_image, text1, text2, text3 FROM tracevia_app.pmv_messages_available WHERE id_reg <> 1 and enabled <> 0");	
		rs = ps.executeQuery();
		
		if (rs != null) {
			while (rs.next()){
				
				 Messages mensagens = new Messages();
				 
				  mensagens.setId_reg(rs.getInt("id_reg"));
				  mensagens.setTipo(rs.getString("type"));
				  mensagens.setNome(rs.getString("name"));
				  mensagens.setId_imagem(rs.getInt("id_image"));	
				  mensagens.setImagem(getImageFromMessageAvailable(rs.getInt("id_image")));
				  mensagens.setTexto1(rs.getString("text1"));	
				  mensagens.setTexto2(rs.getString("text2"));
				  mensagens.setTexto3(rs.getString("text3"));
				  
				  lista.add(mensagens);		
		   }
	   }

	} catch (SQLException e) {
		e.printStackTrace();
	}finally {ConnectionFactory.closeConnection(conn, ps, rs);}

	  
	  return lista;
  }
	 
	 public List<Messages> availableMessagesByType(String type) throws Exception {
		  
	     List<Messages> lista = new ArrayList<Messages>();
	  
	  try {
		  
		  String query = "";
		  
		  if(!type.equals("All"))
		       query = "SELECT id_reg, type, name, id_image, text1, text2, text3 FROM tracevia_app.pmv_messages_available " +
		  		       "WHERE id_reg <> 1 and enabled <> 0 and type = '"+type+"' ";
		  
		  else query = "SELECT id_reg, type, name, id_image, text1, text2, text3 FROM tracevia_app.pmv_messages_available " +
	  		           "WHERE id_reg <> 1 and enabled <> 0 ";
	  
		conn = ConnectionFactory.connectToTraceviaApp(); 
		ps = conn.prepareStatement(query);	
						
		rs = ps.executeQuery();
				
		if (rs != null) {
			while (rs.next()){
				
				 Messages mensagens = new Messages();
				 
				  mensagens.setId_reg(rs.getInt("id_reg"));
				  mensagens.setTipo(rs.getString("type"));
				  mensagens.setNome(rs.getString("name"));
				  mensagens.setId_imagem(rs.getInt("id_image"));	
				  mensagens.setImagem(getImageFromMessageAvailable(rs.getInt("id_image")));
				  mensagens.setTexto1(rs.getString("text1"));	
				  mensagens.setTexto2(rs.getString("text2"));
				  mensagens.setTexto3(rs.getString("text3"));
				  
				  lista.add(mensagens);		
		   }
	   }

	} catch (SQLException e) {
		e.printStackTrace();
	}finally {ConnectionFactory.closeConnection(conn, ps, rs);}

	  
	  return lista;
  }
	 
	 public boolean insertMessage(int imageID, String user, String tipo, String nome, String texto1, String texto2, String texto3) throws Exception {
				        
	        DateTimeApplication dt = new DateTimeApplication();
	        boolean status = false;
			
			if (texto1 == null && texto2 == null && texto3 == null)			
				throw new Exception("O valor passado nao pode ser nulo");
			
			try {
						
			String sql = "INSERT INTO tracevia_app.pmv_messages_available (id_reg, creation_date,  creation_username, type, name, id_image, " +
					     "text1, text2, text3, enabled) " +
					     "VALUES( null, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
			
			//Retorna data atual (data do sistema como parâmetro)
			String dt_creation = dt.currentStringDate(DateTimeApplication.DATE_TIME_FORMAT_STANDARD_DATABASE);
			 
		    conn = ConnectionFactory.connectToTraceviaApp(); 	
	        
			ps = conn.prepareStatement(sql);
			ps.setString(1, dt_creation);
			ps.setString(2, user);
			ps.setString(3, tipo);
			ps.setString(4, nome);
			ps.setInt(5, imageID);
			ps.setString(6, texto1);
			ps.setString(7, texto2);
			ps.setString(8, texto3);
			ps.setBoolean(9, true);
					
			ps.executeUpdate();				
			status = true;
											
		    } catch (SQLException sqle) {
			throw new Exception("Erro ao inserir dados " + sqle);
			
		    } finally	
		      {
			     ConnectionFactory.closeConnection(conn, ps);
		     }
		
		return status;
		
		}
		
		
	public boolean updateMessage(int imageID, String user, String tipo, String nome, String texto1, String texto2, String texto3, String id) throws Exception {
			
	        boolean status = false;
	        DateTimeApplication dt = new DateTimeApplication();
							
			try {
						
			String sql = "UPDATE tracevia_app.pmv_messages_available SET id_image = ?, update_date = ?, update_username = ?, type = ?, name = ?, text1 = ?, text2 = ?, text3 = ? WHERE id_reg = ?";
			
			conn = ConnectionFactory.connectToTraceviaApp(); 
			
			String dt_update = dt.currentStringDate(DateTimeApplication.DATE_TIME_FORMAT_STANDARD_DATABASE);
	        
			ps = conn.prepareStatement(sql);					
			ps.setInt(1, imageID);
			ps.setString(2, dt_update);
			ps.setString(3, user);
			ps.setString(4, tipo);
			ps.setString(5, nome);
			ps.setString(6, texto1);
			ps.setString(7, texto2);
			ps.setString(8, texto3);			
			ps.setInt(9, Integer.parseInt(id));
					
			ps.executeUpdate();				
			status = true;
																	
		    } catch (SQLException sqle) {
		    	
			throw new Exception("Erro ao atualizar dados " + sqle);
			
		    } finally	
		      {
			     ConnectionFactory.closeConnection(conn, ps);
		     }
		
		return status;
		
		}
	
	
	 public Messages mensagensDisponivelById(int id) throws Exception {
		 
		     Messages mensagem = new Messages();
			  
	  try {
	  
		conn = ConnectionFactory.connectToTraceviaApp(); 
		ps = conn.prepareStatement("SELECT id_reg, id_image,type, name, text1, text2, text3 FROM tracevia_app.pmv_messages_available WHERE id_reg = ?");	
		ps.setInt(1, id);
		
		rs = ps.executeQuery();
		
		if (rs != null) {
			while (rs.next()){
												 
				  mensagem.setId_reg(rs.getInt("id_reg"));
				  mensagem.setId_imagem(rs.getInt("id_image"));
				  mensagem.setImagem(getImageFromMessageAvailable(rs.getInt("id_image")));
				  mensagem.setTipo(rs.getString("type"));
				  mensagem.setNome(rs.getString("name"));
				  mensagem.setTexto1(rs.getString("text1"));
				  mensagem.setTexto2(rs.getString("text2"));	
				  mensagem.setTexto3(rs.getString("text3"));	
		    }
	      }

	} catch (SQLException e) {
		e.printStackTrace();
	}finally {ConnectionFactory.closeConnection(conn, ps, rs);}

	  
	  return mensagem;
  }
	 
	 public String getImageFromMessageAvailable(int id) {
		 
		 String image = null;
		 
		 if(id < 10)			 
			 image = "00"+id+"_6464.bmp";
	     
		 else if(id >= 10 && id < 100)	 
	    	 image = "0"+id+"_6464.bmp";
	     
		 else if(id > 100 && id <= 105)	 
	    	image = id+"_6464.bmp";
	    
		 else if(id == 106)
	    	image = "logo ATT.bmp";
	    
		 else if(id == 107)
	    	image =  "logo ATT2.bmp";
	    
	       return image;		 
	 }	
	 
	 
	 public Messages getDMSmessages(int equip) throws Exception {
		 
		 Messages mensagem = new Messages();
		 		  
		  try {
		  
			conn = ConnectionFactory.connectToTraceviaApp(); 
			ps = conn.prepareStatement("SELECT id_reg, id_image, text1, text2, text3 FROM tracevia_app.pmv_messages_available WHERE id_reg = (SELECT id_message FROM tracevia_app.pmv_messages_active WHERE id_equip = ?)");	
			ps.setInt(1, equip);
			
			rs = ps.executeQuery();
			
			if (rs != null) {
				while (rs.next()){
									
					  mensagem.setId_reg(rs.getInt("id_reg"));
					  mensagem.setId_imagem(rs.getInt("id_image"));
					  mensagem.setImagem(getImageFromMessageAvailable(rs.getInt("id_image")));
					  mensagem.setTexto1(rs.getString("text1"));
					  mensagem.setTexto2(rs.getString("text2"));
					  mensagem.setTexto3(rs.getString("text3"));
			    }
		      }

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {ConnectionFactory.closeConnection(conn, ps, rs);}

		  
		  return mensagem; 
		 
	 }
	 
 public Messages selectMessageToUpdate(int equip) throws Exception {
		 
		 Messages mensagem = new Messages();
		 		  
		  try {
			  
			String sql = "SELECT d.id_image, d.text1, d.text2, d.text3, a.id_modify FROM tracevia_app.pmv_messages_available d " + 
			  		     "INNER JOIN tracevia_app.pmv_messages_active a " + 
			  		     "ON a.id_modify = d.id_reg WHERE id_equip = ? ";
		  
			conn = ConnectionFactory.connectToTraceviaApp(); 
			ps = conn.prepareStatement(sql);	
			ps.setInt(1, equip);
			
			rs = ps.executeQuery();
			
			if (rs != null) {
				while (rs.next()){
					
					  mensagem.setId_imagem(rs.getInt("d.id_image"));
					  mensagem.setImagem(getImageFromMessageAvailable(rs.getInt("d.id_image")));
					  mensagem.setTexto1(rs.getString("d.text1"));
					  mensagem.setTexto2(rs.getString("d.text2"));
					  mensagem.setTexto3(rs.getString("d.text3"));
					  mensagem.setId_modify(rs.getInt("a.id_modify"));
			     }
		      }

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {ConnectionFactory.closeConnection(conn, ps, rs);}
		  
		  return mensagem; 		 
	 }
	 
//Messages Actives Bean	 
 public List<Messages> selectActivesMessages() throws Exception { 
		 
		   List<Messages> lista = new ArrayList<Messages>();
		 		  
		  try {
			  			  
			  String sql = "SELECT d.id_image, d.text1, d.text2, d.text3, a.id_equip, a.id_modify, a.active_status " + 
			  		"FROM tracevia_app.pmv_messages_available d " + 
			  		"INNER JOIN tracevia_app.pmv_messages_active a " + 
			  		"ON a.id_message = d.id_reg ";		  		
			  		 							 		  
		    conn = ConnectionFactory.connectToTraceviaApp(); 		    
			ps = conn.prepareStatement(sql);
			
			rs = ps.executeQuery();
						
			if (rs != null) {
				while (rs.next()){
					
					Messages message = new Messages();									
					
					  message.setEquip(rs.getInt("a.id_equip"));
					  message.setId_modify(rs.getInt("a.id_modify"));
					  message.setId_imagem(rs.getInt("d.id_image"));
					  message.setImagem(getImageFromMessageAvailable(rs.getInt("d.id_image")));					
					  message.setTexto1(rs.getString("d.text1"));
					  message.setTexto2(rs.getString("d.text2"));
					  message.setTexto3(rs.getString("d.text3"));
					  message.setActiveMessage(rs.getBoolean("a.active_status"));
					  
					  lista.add(message);
			    }
		      }

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {ConnectionFactory.closeConnection(conn, ps, rs);}
		  
		  return lista; 		 
	 }
   	 	 
 public boolean updateModifyMessage(int messageID, int equip) throws Exception {
		 
	 boolean status = false;
		
		try {
						
	  /* DateFormat dfm = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
		Date date = new Date();
		String strDate = dfm.format(date); s*/
								
		String sql = "UPDATE tracevia_app.pmv_messages_active SET id_modify = ?, active_status = ? WHERE id_equip = ?";
		
		conn = ConnectionFactory.connectToTraceviaApp();	
     
		ps = conn.prepareStatement(sql);		
		ps.setInt(1, messageID);
		ps.setBoolean(2, false);	
		ps.setInt(3, equip);
				
		ps.executeUpdate();				
		status = true;
																
	    } catch (SQLException sqle) {
	    	
		throw new Exception("Erro ao atualizar dados " + sqle);
		
	    } finally	
	     {
		     ConnectionFactory.closeConnection(conn, ps);
	     }
	
	return status;
	
	 }
 
 
 public List<Messages> checkModifyMessageState(int[] equip) throws Exception {
	 
	     List<Messages> list = new ArrayList<Messages>();
	     
	     int count = 0;
			
		 for(int i = 0; i < equip.length; i++) {
			   if(equip[i] != 0)
				   count++;
		 }	 
		
		try {
							
		String query = "SELECT p.name, m.active_status  FROM tracevia_app.pmv_messages_active m " +
				       "INNER JOIN tracevia_app.pmv_equipment p ON p.equip_id = m.id_equip " +
				       "WHERE m.id_equip IN("; 
		
		for(int i = 0; i < count; i++) {	
			query += "'"+equip[i]+"'";
		
		if(equip[i] != equip[count-1]) //Caso seja maior que 1 => equip , então add ','
			query += " , ";				
					
		else query += ") ";
				
		}
		
		//System.out.println("COUNT: "+count);
		
		conn = ConnectionFactory.connectToTraceviaApp();	
     
		ps = conn.prepareStatement(query);	
		
		//System.out.println(query);
						
		rs = ps.executeQuery();
		
		if (rs != null) {
			while (rs.next()){
			
			   Messages msg = new Messages();
			   
			   msg.setNome(rs.getString(1));
			   msg.setActiveMessage(rs.getBoolean(2));
			   
			   list.add(msg);
			    
		    }
	      }
																
	    } catch (SQLException sqle) {
	    	
		throw new Exception("Erro ao recuperar dados " + sqle);
		
	    } finally	
	      {
		     ConnectionFactory.closeConnection(conn, ps);
	     }
	
	return list;
	
	 }
 
 
 public boolean removeRegister(int id) throws Exception  {
	 
     boolean response = false;
		
		if (id == 0)
			throw new Exception("O valor passado nao pode ser nulo");
		
		try {
			
   String sql = "UPDATE tracevia_app.pmv_messages_available SET enabled = 0 WHERE id_reg = ?";

        conn = ConnectionFactory.connectToTraceviaApp();	
        
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1, id);
		
			ps.executeUpdate();
			
			response = true;
					
		    } catch (SQLException sqle) {
			throw new Exception("Erro ao atualizar dado " + sqle);
		} finally {
			ConnectionFactory.closeConnection(conn, ps);
		}
		
		return response; 
   }
 
   public Integer verifyRegisterIsActive(int id) throws Exception  {
	 
        int response = 0;
		
		if(id == 0)
			throw new Exception("O valor passado nao pode ser nulo");
		
		try {
			
            String sql = "SELECT COUNT(ac.active_status) AS status FROM tracevia_app.pmv_messages_available av " +
		   "INNER JOIN tracevia_app.pmv_messages_active ac ON ac.id_message = av.id_reg " +		   
		   "WHERE av.id_reg = ? AND ac.active_status IN(0,1) ";

            conn = ConnectionFactory.connectToTraceviaApp();	
        
			ps = conn.prepareStatement(sql);			
			ps.setInt(1, id);	
			
			rs = ps.executeQuery();
			
			if (rs != null) {
				while (rs.next()){
					
				 response = rs.getInt("status");
				 				   
			    }
		      }			
					
		    } catch (SQLException sqle) {
			throw new Exception("Erro ao atualizar dado " + sqle);
		} finally {
			ConnectionFactory.closeConnection(conn, ps);
		}
		
		return response; 
   }
 
 /*
	 
 public boolean deletarRegistro(int id) throws Exception {
		
		boolean response = false;
		
		if (id == 0)
			throw new Exception("O valor passado nao pode ser nulo");
		
		try {
			
   String sql = "DELETE FROM tracevia_app.pmv_messages_available WHERE id_reg = ?";

        conn = ConnectionFactory.connectToTraceviaApp();	
        
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1, id);
		
			ps.executeUpdate();
			
			response = true;
					
		    } catch (SQLException sqle) {
			throw new Exception("Erro ao deletar dados " + sqle);
		} finally {
			ConnectionFactory.closeConnection(conn, ps);
		}
		
		return response;
	}	 
 */
  
 public boolean updateActivesMessages(int[] msg, int[] equip) throws Exception {
	 
	 boolean status = false;
		
		try {
			
		 int count = 0;
			
		 for(int i = 0; i < equip.length; i++) {
			   if(equip[i] != 0)
				   count++;
			   
			   //System.out.println(equip[i]);
		 }	 
						
	  /* DateFormat dfm = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
		Date date = new Date();
		String strDate = dfm.format(date); s*/
				
		String sql = "";
		
		for(int i = 0; i < count; i++) {	
								
		//sql = "UPDATE tb_mensagens_ativas SET id_mensagem = '"+msg[i]+"' WHERE id_equip = '"+equip[i]+"' ";
		
		sql = "UPDATE tracevia_app.pmv_messages_active SET id_modify = '"+msg[i]+"', active_status = false WHERE id_equip = '"+equip[i]+"' ";
			
		conn = ConnectionFactory.connectToTraceviaApp();	     
		ps = conn.prepareStatement(sql);		
		
		ps.executeUpdate();	
		
		}
		
		status = true;
																
	    } catch (SQLException sqle) {
	    	
		throw new Exception("Erro ao atualizar dados " + sqle);
		
	    } finally	
	     {
		     ConnectionFactory.closeConnection(conn, ps);
	     }
	
	return status;
	
	 }
 
 
public boolean updateCleanMessages(int msg, int[] equip) throws Exception {
	 
	 boolean status = false;
		
		try {
			
		int count = 0;
				
		for(int i = 0; i < equip.length; i++) {
			if(equip[i] != 0)
				count++;
		}	 
						
	  /* DateFormat dfm = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
		Date date = new Date();
		String strDate = dfm.format(date); s*/
				
		String sql = "";
		
		for(int i = 0; i < count; i++) {	
								
		sql = "UPDATE tracevia_app.pmv_messages_active SET id_message = '"+msg+"' WHERE id_equip = '"+equip[i]+"' ";	
		
		//sql = "UPDATE tracevia_app.pmv_messages_active SET id_modify = '"+msg+"', active_status = false WHERE id_equip = '"+equip[i]+"' ";
						
		conn = ConnectionFactory.connectToTraceviaApp();	
     
		ps = conn.prepareStatement(sql);		
		
		ps.executeUpdate();	
		
		}
		
		status = true;
																
	    } catch (SQLException sqle) {
	    	
		throw new Exception("Erro ao atualizar dados " + sqle);
		
	    } finally	
	     {
		     ConnectionFactory.closeConnection(conn, ps);
	     }
	
	return status;
	
	 }
 
  
}
