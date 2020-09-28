package br.edu.utfpr.dv.sireata.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.edu.utfpr.dv.sireata.model.Anexo;

public class AnexoDAO {
	
	public Anexo buscarPorId(int id) throws SQLException{
		String sql = "SELECT anexos.* FROM anexos WHERE idAnexo = ?";

		try(
			Connection conn = ConnectionDAO.getInstance().getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.setInt(1, id).executeQuery();
		){
			if(rs.next()){
				return this.carregarObjeto(rs);
			}else{
				return null;
			}
		}
	}
	
	public List<Anexo> listarPorAta(int idAta) throws SQLException{
		String sql = "SELECT anexos.* FROM anexos WHERE idAta = ? ORDER BY anexos.ordem"

		try(
			Connection conn = ConnectionDAO.getInstance().getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.setInt(1, idAta).executeQuery();
		){
			List<Anexo> list = new ArrayList<Anexo>();
			
			while(rs.next()){
				list.add(this.carregarObjeto(rs));
			}
			
			return list;
		}
	}
	
	public int salvar(Anexo anexo) throws SQLException{
		boolean insert = (anexo.getIdAnexo() == 0);
		// Colocando INSERT e UPDATE em funções separadas para melhor entendimento do código
		if (insert) {
			return insert(anexo);
		} else {
			return update(anexo);
		}
	}
		
	private int insert(Anexo anexo) throws SQLException {
		try(
			Connection conn = ConnectionDAO.getInstance().getConnection();
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO anexos(idAta, ordem, descricao, arquivo) VALUES(?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
		){
			stmt.setInt(1, anexo.getAta().getIdAta());
			stmt.setInt(2, anexo.getOrdem());
			stmt.setString(3, anexo.getDescricao());
			stmt.setBytes(4, anexo.getArquivo());

			int rows = stmt.executeUpdate();
			if(rows == 0){
				throw new SQLException("Falha ao inserir no banco.");
			}

			try(
				ResultSet rs = stmt.getGeneratedKeys();
			){
				if(rs.next()){
					anexo.setIdAnexo(rs.getInt(1));
				}
				return anexo.getIdAnexo();
			}
		}
	}

	private int update(Anexo anexo) throws SQLException {
		try(
			Connection conn = ConnectionDAO.getInstance().getConnection();
			PreparedStatement stmt = conn.prepareStatement("UPDATE anexos SET idAta=?, ordem=?, descricao=?, arquivo=? WHERE idAnexo=?");
		){
			stmt.setInt(1, anexo.getAta().getIdAta());
			stmt.setInt(2, anexo.getOrdem());
			stmt.setString(3, anexo.getDescricao());
			stmt.setBytes(4, anexo.getArquivo());
			stmt.setInt(5, anexo.getIdAnexo());

			int rows = stmt.executeUpdate();
			if(rows == 0){
				throw new SQLException("Falha ao fazer update no banco, id não encontrado.");
			}
			
			return anexo.getIdAnexo();
		}
	}
	
	public void excluir(int id) throws SQLException{
		String sql = "DELETE FROM anexos WHERE idanexo=?"
		
		try(
			Connection conn = ConnectionDAO.getInstance().getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
		){
			stmt.setInt(1, id)
			int rs = stmt.executeUpdate();
		}
	}
	
	private Anexo carregarObjeto(ResultSet rs) throws SQLException{
		Anexo anexo = new Anexo();
		
		anexo.setIdAnexo(rs.getInt("idAnexo"));
		anexo.getAta().setIdAta(rs.getInt("idAta"));
		anexo.setDescricao(rs.getString("descricao"));
		anexo.setOrdem(rs.getInt("ordem"));
		anexo.setArquivo(rs.getBytes("arquivo"));
		
		return anexo;
	}

}
