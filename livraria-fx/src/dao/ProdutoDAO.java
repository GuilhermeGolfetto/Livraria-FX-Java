package dao;

/**Cuida do acesso ao dados da classe {@link Produto}.
 * @Autor Guilherme Golfetto <golfetto.guilherme@gmail.com.br>
 * @since 1.0
 * */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import br.com.casadocodigo.livraria.Autor;
import br.com.casadocodigo.livraria.produtos.LivroFisico;
import br.com.casadocodigo.livraria.produtos.Produto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProdutoDAO {
	/**
	 * Lista todos os produtos do banco de dados
	 * 
	 * @return {@link ObservableList} com todos os produtos
	 * 
	 * @thros RuntimeException em caso de erros
	 */
	public ObservableList<Produto> lista() {
		ObservableList<Produto> produtos = FXCollections.observableArrayList();

		PreparedStatement ps;

		try (Connection conn = new ConnectionFactory().getConnection()) {
			ps = conn.prepareStatement("SELECT * FROM produtos");

			ResultSet resultSet = ps.executeQuery();
			while (resultSet.next()) {

				LivroFisico livro = new LivroFisico(new Autor());
				livro.setNome(resultSet.getString("nome"));
				livro.setDescricao(resultSet.getString("descricao"));
				livro.setValor(resultSet.getDouble("valor"));
				livro.setIsbn(resultSet.getString("isbn"));

				produtos.add(livro);

			}

			resultSet.close();
			ps.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return produtos;

	}
	/**@param produto que deverá ser adicionado no banco
	 * @throws Runtime Exception*/
	public void adciona(Produto produto) {
		PreparedStatement ps;

		try (Connection conn = new ConnectionFactory().getConnection()) {

			ps = conn.prepareStatement("INSERT INTO produtos(nome,descricao,valor,isbn)VALUES(?,?,?,?);");

			ps.setString(1, produto.getNome());
			ps.setString(2, produto.getDescricao());
			ps.setDouble(3, produto.getValor());
			ps.setString(4, produto.getIsbn());

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

}
