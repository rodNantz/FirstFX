package app.fxmltableview.base;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import app.fxmltableview.model.Carrier;

public class CarrierDAO {

		private final String stmtConsultaCarrier = "SELECT * from carrier WHERE id = ?"; // WHERE nomeRazaoS LIKE ?

		private final String stmtAllCarrier = "CALL sp_pessoa_SELECTLIKE(?)";
		//private final String stmtConsultaPessoa = "SELECT * from pessoa WHERE nomeRazaoS LIKE ?";
		
		private final String stmtInserirCarrier = "CALL sp_pessoa_INSERT(?,?,?,?,?,?,?,?)";
		/*
		private final String stmtInserirPessoa = "INSERT INTO pessoa (nomerazaos, apelidonomefant, "
				+ "cpfcnpj, tipopessoa, datanascabertura, estcivilrf, rginscricaoest, endereco_codendereco) "
				+ " VALUES (?,?,?,?,?,?,?,?) ";
		*/
		
		private final String stmtUpdateCarrier = "CALL sp_pessoa_UPDATE(?,?,?,?,?,?,?,?)";
		/*
		private final String stmtUpdatePessoa = "UPDATE pessoa SET nomerazaos=?, apelidonomefant=?, cpfcnpj=?, tipopessoa=?, "
				+ "datanascabertura=?, estcivilrf=?, rginscricaoest=?, endereco_codendereco=? WHERE cpfcnpj=? ";
		*/
		
		private final String stmtRemoveCarrier = "CALL sp_pessoa_DELETE(?)";
		// private final String stmtRemovePessoa = "DELETE FROM pessoa WHERE cpfcnpj = ?";

		
		// select de lista com todas as pessoas
		public List<Carrier> ConsultaCarrier(Carrier carrier) throws ClassNotFoundException{
			Connection con = null;
			PreparedStatement stmt = null;
			//CallableStatement stmt = null; //for procedures
			ResultSet rs = null;
			Carrier c = null;
			ArrayList<Carrier> lista = new ArrayList<>();
			try {
				con = ConnectionFactory.getConnection();
				stmt = con.prepareStatement(stmtConsultaCarrier); 
				//stmt = con.prepareCall(stmtConsultaCarrier); //for procedures
				stmt.setString(1, carrier.getId());
				//stmt.setString(1, "%%"); // qlqr um	
				rs = stmt.executeQuery();
				while(rs.next()){
					c = new Carrier(rs.getString("id"),
							rs.getString("code"),
							rs.getString("name"));

					lista.add(c);
				}
				rs.close();
				stmt.close();
				con.close();
			} catch(Exception e){
				System.err.println("(@ConsultaCarrier)Erro:" + e.getMessage());
				e.printStackTrace();
			}
			return lista;
		}
		/*
		// inserir pessoa
		public boolean inserirPessoa(Pessoa pessoa) throws SQLException, ClassNotFoundException, Exception {
			Connection con = null;
			PreparedStatement stmt = null;
			//ResultSet rs = null;
			//Pessoa p = null;
			Boolean passed = false;
			
			try {
				con = ConnectionFactory.getConnection();
				//con.setAutoCommit(false);
				stmt = con.prepareStatement(stmtInserirPessoa);
				String log;
				log = pessoa.getTipoPessoa()+";"+pessoa.getNomeOuRazaoSocial();
				if(pessoa.getTipoPessoa().equalsIgnoreCase("F")){
					PessoaFisica f = (PessoaFisica) pessoa;
					stmt.setString(1, f.getNomeOuRazaoSocial()); 
					stmt.setString(2, f.getApelidoNomeFantasia());
					stmt.setString(3, f.getCpf());
					stmt.setString(4, f.getTipoPessoa());
					stmt.setString(5, f.parseDataBD(f.getDataNascimento()));
					stmt.setString(6, f.getEstadoCivil());
					stmt.setString(7, f.getRg());
					stmt.setInt(8, f.getCodEndereco());
					stmt.executeUpdate();
					System.out.println("Inseriu pessoa física com sucesso!");
					log = f.getTipoPessoa()+f.getNomeOuRazaoSocial()+f.getCpf();
					passed = true;
				} else if (pessoa.getTipoPessoa().equalsIgnoreCase("J")){
					PessoaJuridica j = (PessoaJuridica) pessoa;
					stmt.setString(1, j.getNomeOuRazaoSocial());
					stmt.setString(2, j.getApelidoNomeFantasia());
					stmt.setString(3, j.getCnpj());
					stmt.setString(4, j.getTipoPessoa());
					stmt.setString(5, j.parseDataBD(j.getDataAbertura()));
					stmt.setString(6, j.getRf());
					stmt.setString(7, j.getInsEstadual());
					stmt.setInt(8, j.getCodEndereco());
					stmt.executeUpdate();
					System.out.println("Inseriu pessoa jurídica com sucesso!");
					log = j.getTipoPessoa()+j.getNomeOuRazaoSocial()+j.getCnpj();
					passed = true;
				}
				stmt.close();
				con.close();
				if (!passed){
					throw new Exception("Pessoa: "+log+" Não inserida");
				} else {
					return passed;
				}
			} catch(SQLException e){
				//e.printStackTrace();
				System.out.println("(@InsertPessoa) Erro "+e.getErrorCode()+" - "+ e.getMessage());
				// throw de exception criada
				if(e.getErrorCode() == 1062){
					throw new SQLSomeException("Dado duplicado! Insira informação única.");
				}
				throw new SQLException(e.getMessage());
			} catch (ClassNotFoundException ex){
				System.out.println("(@inserirPessoa)Erro: " + ex.getMessage());
				//ex.printStackTrace();
				throw new ClassNotFoundException("(@inserirPessoa)Erro: " + ex.getMessage());
			} catch(Exception exc){
				System.out.println("(@inserirPessoa)Erro: " + exc.getMessage());
				//exc.printStackTrace();
				throw new Exception("(@inserirPessoa)Erro: " + exc.getMessage());
			}
			//return true;
		}
		
		// update pessoa
		public boolean updatePessoa(Pessoa pessoa) throws SQLSomeException, SQLException, ClassNotFoundException, Exception {
			Connection con = null;
			PreparedStatement stmt = null;
			//ResultSet rs = null;
			//Pessoa p = null;
			boolean b = false;
			try {
				con = ConnectionFactory.getConnection();
				stmt = con.prepareStatement(stmtUpdatePessoa);
				if(pessoa.getTipoPessoa().equalsIgnoreCase("F")){
					PessoaFisica f = (PessoaFisica) pessoa;
					stmt.setString(1, f.getNomeOuRazaoSocial()); 
					stmt.setString(2, f.getApelidoNomeFantasia());
					stmt.setString(3, f.getCpf());
					stmt.setString(4, f.getTipoPessoa());
					stmt.setString(5, f.parseDataBD(f.getDataNascimento()));
					stmt.setString(6, f.getEstadoCivil());
					stmt.setString(7, f.getRg());
					stmt.setInt(8, f.getCodEndereco());
					// where (usado no stmt antigo, sem procedure)
					//stmt.setString(9, f.getCpf());		
					stmt.executeUpdate();
					System.out.println("Update de pessoa física "+f.getCpf()+" com sucesso!");
				} else if(pessoa.getTipoPessoa().equalsIgnoreCase("J")){
					PessoaJuridica j = (PessoaJuridica) pessoa;
					stmt.setString(1, j.getNomeOuRazaoSocial());
					stmt.setString(2, j.getApelidoNomeFantasia());
					stmt.setString(3, j.getCnpj());
					stmt.setString(4, j.getTipoPessoa());
					stmt.setString(5, j.parseDataBD(j.getDataAbertura()));
					stmt.setString(6, j.getRf());
					stmt.setString(7, j.getInsEstadual());
					stmt.setInt(8, j.getCodEndereco());
					//where (usado no stmt antigo, sem procedure)
					//stmt.setString(9, j.getCnpj());
					stmt.executeUpdate();
					System.out.println("Update de pessoa jurídica com sucesso!");
				}
				stmt.close();
				con.close();
				b = true;
			} catch(SQLException e){
				//e.printStackTrace();
				System.out.println("(@UpdatePessoa) Erro "+e.getErrorCode()+" - "+ e.getMessage());
				// throw de exception criada
				if(e.getErrorCode() == 1062){
					throw new SQLSomeException("Dado duplicado! Insira informação única.");
				}
				throw new SQLException(e.getMessage());
			} catch (ClassNotFoundException ex){
				//ex.printStackTrace();
				System.out.println("(@UpdatePessoa) Erro: " + ex.getMessage());
				throw new ClassNotFoundException(ex.getMessage());
			} catch (Exception exc){
				//exc.printStackTrace();
				System.out.println("(@UpdatePessoa) Erro: " + exc.getMessage());
				throw new Exception(exc.getMessage());
			}
			return b;

		}
		
		public boolean removePessoa(Pessoa p) throws SQLException, ClassNotFoundException, Exception {
			Connection con = null;
			PreparedStatement stmt = null;
			try {
				con = ConnectionFactory.getConnection();
				stmt = con.prepareStatement(stmtRemovePessoa);
				String cpfcnpj = null;
				if (p.getTipoPessoa().equals("F")){
					PessoaFisica f = (PessoaFisica) p;
					cpfcnpj = f.getCpf();
				} else if (p.getTipoPessoa().equals("J")){
					PessoaJuridica j = (PessoaJuridica) p;
					cpfcnpj = j.getCnpj();
				}
				stmt.setString(1, cpfcnpj);
				stmt.executeUpdate();
				System.out.println("Deleted sucessfully - pessoa " + cpfcnpj);
			} catch(SQLException e){
				e.printStackTrace();
				System.out.println("(@removePessoa) Erro: " + e.getMessage());
				throw new SQLException(e.getMessage());
			} catch (ClassNotFoundException ex){
				ex.printStackTrace();
				System.out.println("(@removePessoa) Erro: " + ex.getMessage());
				throw new ClassNotFoundException(ex.getMessage());
			} catch (Exception e) {
				System.out.println("(@removePessoa) Erro: "+ e.getMessage());
			}
			
			return false;
		}
		*/


}
