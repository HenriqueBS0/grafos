import java.util.HashMap;
import java.util.LinkedList;

public class ListaAdjacencia {
    private HashMap<Integer, LinkedList<Adjacencia>> lista = new HashMap<Integer, LinkedList<Adjacencia>>();

    public void inserir (int origem, int destino) {
        this.inserir(origem, destino, 1);
    }

    public void inserir(int origem, int destino, int peso) {

        if(this.lista.get(origem) == null) {
            this.lista.put(origem, new LinkedList<Adjacencia>());
        }

        if(this.getIndiceAdjacencia(origem, destino) != -1) {
            return;
        }

        this.lista.get(origem).add(new Adjacencia(destino, peso));
    }

    public void remover(int origem, int destino) {
        lista.get(origem).remove(getIndiceAdjacencia(origem, destino));
    }

    public int peso(int origem, int destino) {
        return lista.get(origem).get(getIndiceAdjacencia(origem, destino)).getPeso();
    }

    private int getIndiceAdjacencia(int origem, int destino) {
        int indice = 0;
        
        for (Adjacencia adjacencia : lista.get(origem)) {
            if(adjacencia.getIndiceNodoDestino() == destino) {
                return indice;
            }
            indice++;
        }

        return -1;
    }

    public HashMap<Integer, LinkedList<Adjacencia>> getLista() {
        return lista;
    }

    public LinkedList<Adjacencia> getListaAdjacencia(int origem) {
        return this.lista.get(origem);
    }

    public boolean existeAresta(int origem, int destino) {
        if(lista.get(origem) == null) {
            return false;
        } 

        for (Adjacencia adjacencia : lista.get(origem)) {
            if(adjacencia.getIndiceNodoDestino() == destino) {
                return true;
            }
        }

        return false;
    } 

    @Override
    public String toString() {
        return lista.toString();
    }
}
