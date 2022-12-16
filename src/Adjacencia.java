public class Adjacencia {
    private int indiceNodoDestino;
    private int peso;

    public Adjacencia(int indiceNodoDestino, int peso) {
        this.indiceNodoDestino = indiceNodoDestino;
        this.peso = peso;
    }

    public int getIndiceNodoDestino() {
        return this.indiceNodoDestino;
    }
    
    public int getPeso() {
        return this.peso;
    }

    @Override
    public String toString() {
        return "{" +  indiceNodoDestino + "," + peso + "}";
    }        
}