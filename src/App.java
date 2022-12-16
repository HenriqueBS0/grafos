public class App {
    public static void main(String[] args) throws Exception {
        Grafo<Integer> grafo = Grafo.criarGrafo();

        System.out.println(grafo.getListaAdjacencia());
        System.out.println(grafo.buscaProfundidade(0, 1));
    }
}
