import java.util.HashMap;

public class MatrizAdjacencia {
    private HashMap<Integer, HashMap<Integer, Integer>> matriz = new HashMap<Integer, HashMap<Integer, Integer>>();

    public void inserir(int origem, int destino) {
        inserir(origem, destino, 1);
    }

    public void inserir(int origem, int destino, int peso) {
        if(matriz.get(origem) == null) {
            matriz.put(origem, new HashMap<Integer, Integer>());
        }

        if(matriz.get(origem).get(destino) != null) {
            return;
        }

        matriz.get(origem).put(destino, peso);
    }

    public void remover(int origem, int destino) {
        matriz.get(origem).put(destino, 0);
    }

    public int peso(int origem, int destino) {
        if(matriz.get(origem) == null || matriz.get(origem).get(destino) == null) {
            return 0;
        }

        return matriz.get(origem).get(destino);
    }

    @Override
    public String toString() {
        return matriz.toString();
    }
}
