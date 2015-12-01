package application;

import java.io.IOException;

import br.com.casadocodigo.livraria.produtos.Produto;
import dao.Exportador;
import dao.ProdutoDAO;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
/** Cuida a interface grafica e organiza as demais classes
 * @author Guilherme Golfetto <golfetto.guilher@gmail.com>
 * @since 1.0*/
@SuppressWarnings({ "unchecked", "rawtypes" })
public class Main extends Application {
	/**Inicializador dos componentes da Classe
	 * @param Stage - Frame iniciado
	 * */
	@Override
	public void start(Stage primaryStage) {
		Group group = new Group();
		
		Scene scene = new Scene(group, 690, 510); 
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
		ObservableList<Produto> produtos = new ProdutoDAO().lista();
		
		TableView tableView = new TableView<>(produtos); 
		TableColumn nomeColumn = new TableColumn("Nome");
		nomeColumn.setMinWidth(180); 
		nomeColumn.setCellValueFactory(new PropertyValueFactory("nome"));
		TableColumn descricaoColumn = new TableColumn("Descrição");
		descricaoColumn.setMinWidth(230);
		descricaoColumn.setCellValueFactory(new PropertyValueFactory("descricao"));
		TableColumn valorColumn = new TableColumn("Valor");
		valorColumn.setMinWidth(60);
		valorColumn.setCellValueFactory(new PropertyValueFactory("valor"));
		TableColumn isbnColumn = new TableColumn("ISBN");
		isbnColumn.setMinWidth(180);
		isbnColumn.setCellValueFactory(new PropertyValueFactory("isbn"));

		tableView.getColumns().addAll(nomeColumn, descricaoColumn, valorColumn, isbnColumn);

		final VBox vbox = new VBox(tableView);
		vbox.setId("vbox");

		Label label = new Label("Listagem de Livros");
		label.setId("label-listagem");
		
		Label progresso = new Label();
		progresso.setId("Progresso");
		
		double valorTotal = 0.0;
		
		for(Produto produto: produtos){
			valorTotal += produto.getValor();
		}
		
		Label labelFooter = new Label(String.format("Você tem R$%.2f em estoque," + "com um total de %d produtos", valorTotal, produtos.size()));
		labelFooter.setId("label-footer");
		
		Button button = new Button("Exportar CSV");
		
		button.setOnAction(event -> {
			Task<Void> task = new Task<Void>() {

				@Override
				protected Void call() throws Exception {
					dormePorDoisSegundos();
					exportarEmCSV(produtos);
					return null;
				}

			};

			task.setOnRunning(e1 -> progresso.setText("Exportando..."));
			task.setOnSucceeded(e2 -> progresso.setText("Concluido"));

			new Thread((Runnable) task).start();
		});

		group.getChildren().addAll(label, vbox, button, progresso,labelFooter);

		primaryStage.setScene(scene);
		primaryStage.setTitle("Sistema da livraria com Java FX");
		primaryStage.setResizable(false);
		primaryStage.show();

	}

	/**Aciona o envio da tabela para o formado CSV
	 * @param Lista que será exportada*/
	public void exportarEmCSV(ObservableList<Produto> produtos) {
		
		try {
			new Exportador().paraCSV(produtos);
		} catch (IOException e) {
			System.out.println("Erro ao exportar: " + e);
		}
	}

	/**Thread de sleep*/
	private void dormePorDoisSegundos() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			System.out.println("Ops, deu ruim:" + e);
		}
	}
	/**Inicio do Programa*/
	public static void main(String[] args) {
		launch(args);
	}

}
