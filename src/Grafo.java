import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

public class Grafo<T> {

    private boolean direcionado;
    private boolean ponderado;
    private MatrizAdjacencia matrizAdjacencia = new MatrizAdjacencia();
    private ListaAdjacencia listaAdjacencia = new ListaAdjacencia();

    private ArrayList<NodoGrafo<T>> elementos = new ArrayList<NodoGrafo<T>>();
    
    public Grafo(boolean direcionado, boolean ponderado) {
        this.direcionado = direcionado;
        this.ponderado = ponderado;
    }

    public int inserir(T dado) {
        int indice = this.elementos.size();

        this.elementos.add(new NodoGrafo<T>(dado));

        return indice;
    }

    public void inserirAresta(int origem, int destino) {
        inserirAresta(origem, destino, 1);
    }

    public void inserirAresta(int origem, int destino, int peso) {
        inserirListaAdjacencia(origem, destino, peso);
        inserirMatrizAdjacencia(origem, destino, peso);
    }

    public void removerAresta(int origem, int destino) {
        removerListaAdjacencia(origem, destino);
        removerMatrizAdjacencia(origem, destino);
    }

    public void inserirListaAdjacencia(int origem, int destino) {
        inserirListaAdjacencia(origem, destino, 1);
    }

    public void inserirListaAdjacencia(int origem, int destino, int peso) {
        peso = this.ponderado ? peso : 1;

        listaAdjacencia.inserir(origem, destino, peso);

        if(this.direcionado || origem == destino) {
            return;
        }

        listaAdjacencia.inserir(destino, origem, peso);
    }

    public void removerListaAdjacencia(int origem, int destino) {
        listaAdjacencia.remover(origem, destino);

        if(this.direcionado || origem == destino) {
            return;
        }

        listaAdjacencia.remover(destino, origem);
    }

    public void inserirMatrizAdjacencia(int origem, int destino) {
        inserirMatrizAdjacencia(origem, destino, 1);
    }

    public void inserirMatrizAdjacencia(int origem, int destino, int peso) {
        peso = this.ponderado ? peso : 1;

        matrizAdjacencia.inserir(origem, destino, peso);

        if(this.direcionado || origem == destino) {
            return;
        }

        matrizAdjacencia.inserir(destino, origem, peso);
    }

    public void removerMatrizAdjacencia(int origem, int destino) {
        matrizAdjacencia.remover(origem, destino);
        
        if(this.direcionado || origem == destino) {
            return;
        }

        matrizAdjacencia.remover(destino, origem);
    }

    public LinkedList<Adjacencia> getListaAdjacenciaVertice(T dado) {
        return listaAdjacencia.getListaAdjacencia(this.getIndiceDado(dado));
    }

    public boolean listaAdjacenciaVerticeVazia(T dado) {
        return getListaAdjacenciaVertice(dado) == null 
            || getListaAdjacenciaVertice(dado).isEmpty();
    }

    private int getIndiceDado(T dado) {
        int indice = 0;

        for (NodoGrafo<T> nodoGrafo : elementos) {
            if(nodoGrafo.getDado() == dado) {
                return indice;
            }

            indice++;
        }

        return indice;
    }

    public ArrayList<NodoGrafo<T>> getElementos() {
        return this.elementos;
    }
    
    public MatrizAdjacencia getMatrizAdjacencia() {
        return matrizAdjacencia;
    }

    public ListaAdjacencia getListaAdjacencia() {
        return listaAdjacencia;
    }

    public ArrayList<HashMap<String, Integer>> getMatrizIncidencia() {
        ArrayList<HashMap<String, Integer>> matrizIncidencia = 
            new ArrayList<HashMap<String, Integer>>();

        for (int linha = 0; linha < elementos.size(); linha++) {
            matrizIncidencia.add(new HashMap<String, Integer>());
            
            for (Integer origemAdjacencia = 0; 
                origemAdjacencia < getListaAdjacencia().getLista().size(); 
                origemAdjacencia++
            ) {
                LinkedList<Adjacencia> adjacencias = getListaAdjacencia()
                    .getLista()
                    .get(origemAdjacencia);

                for (Adjacencia adjacencia : adjacencias) {

                    String chave = 
                        "{" + origemAdjacencia + "-" + adjacencia.getIndiceNodoDestino() + "}";

                    if(
                        linha == origemAdjacencia 
                        || (!direcionado && linha == adjacencia.getIndiceNodoDestino())
                    ) {
                        matrizIncidencia.get(linha).put(chave, adjacencia.getPeso());
                    }
                    else {
                        matrizIncidencia.get(linha).put(chave, 0);
                    }
                }
            }

        }
        return matrizIncidencia;
    }

    public boolean isCompleto() {
        for (int origem = 0; origem < elementos.size(); origem++) {
            for (int destino = 0; destino < elementos.size(); destino++) {
                if(origem == destino) {
                    continue;
                }

                if(!this.getListaAdjacencia().existeAresta(origem, destino)) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean buscaLargura(int origem, T dado) {
        ArrayList<Integer> elementosMapeados = new ArrayList<Integer>();
        
        ArrayList<Integer> proximosElentos = new ArrayList<Integer>(); 

        proximosElentos.add(origem);

        while(!proximosElentos.isEmpty()) {
            ArrayList<Integer> elementosAtuais = proximosElentos;
            proximosElentos = new ArrayList<Integer>();

            for (Integer elemento : elementosAtuais) {
                if(elementosMapeados.indexOf(elemento) != -1) {
                    continue;
                }

                if(elementos.get(elemento).getDado() == dado) {
                    return true;
                }

                for (Adjacencia adjacencia : getListaAdjacencia().getLista().get(elemento)) {
                    proximosElentos.add(adjacencia.getIndiceNodoDestino());
                }

                elementosMapeados.add(elemento);
            }
        }

        return false;
    } 

    public boolean buscaProfundidade(int origem, T dado) {
        return buscaProfundidade(origem, dado, new ArrayList<Integer>());
    }

    private boolean buscaProfundidade(int origem, T dado, ArrayList<Integer> elementosMapeados) {
        if(elementosMapeados.indexOf(origem) != -1) {
            return false;
        }

        if(elementos.get(origem).getDado() == dado) {
            return true;
        }

        elementosMapeados.add(origem);

        for (Adjacencia adjacencia : getListaAdjacencia().getLista().get(origem)) {
            if(buscaProfundidade(adjacencia.getIndiceNodoDestino(), dado, elementosMapeados)) {
                return true;
            }
        }

        return false;
    } 

    public static Grafo<Integer> criarGrafo() {
        Scanner entrada  = new Scanner(System.in);

        System.out.println("Grafo Direcionado (S|N): ");
        boolean direcionado = entrada.nextLine().toUpperCase().equals("S");
        System.out.println("Grafo Ponderado (S|N): ");
        boolean ponderado = entrada.nextLine().toUpperCase().equals("S");

        Grafo<Integer> grafo = new Grafo<Integer>(direcionado, ponderado);

        System.out.println("Número de Elementos: ");

        int numeroElementos = entrada.nextInt();

        for (int i = 1; i <= numeroElementos; i++) {
            System.out.println("Informe o " + i + "º elemento: ");
            grafo.inserir(entrada.nextInt());
            entrada.nextLine();
        }

        for (int indicePrimeiroElemento = 0; indicePrimeiroElemento < numeroElementos; indicePrimeiroElemento++) {
            for (int indiceSegundoElemento = 0; indiceSegundoElemento < numeroElementos; indiceSegundoElemento++) {
                if(!direcionado && indiceSegundoElemento  < indicePrimeiroElemento) {
                    continue;
                }

                int indiceTextualPrimeiro = indicePrimeiroElemento+ 1;
                int indiceTextualSegundo = indiceSegundoElemento + 1;

                System.out.println("O " + indiceTextualPrimeiro + "º elemento tem ligação com o " + indiceTextualSegundo + "º (S|N): ");

                if(!entrada.nextLine().toUpperCase().equals("S")) {
                    continue;
                }

                if(!ponderado) {
                    grafo.inserirAresta(indicePrimeiroElemento, indiceSegundoElemento);
                    continue;
                }

                System.out.println("Peso da aresta: ");
                grafo.inserirAresta(indicePrimeiroElemento, indiceSegundoElemento, entrada.nextInt());
                entrada.nextLine();
            }
        }

        entrada.close();

        return grafo;
    }
}
