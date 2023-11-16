import java.util.Random;

class Usuario {
    private String nombre;
    private int nip;
    private int saldo;

    public Usuario(String nombre, int nip) {
        this.nombre = nombre;
        this.nip = nip;
        this.saldo = new Random().nextInt(49001) + 1000;
    }

    public String getNombre() {
        return nombre;
    }

    public int getSaldo() {
        return saldo;
    }

    public boolean verificarAcceso(String nombre, int nip) {
        return this.nombre.equalsIgnoreCase(nombre) && this.nip == nip;
    }

    public boolean retirarEfectivo(int monto) {
        if (monto < 100 || monto > saldo) {
            return false;
        }
        saldo -= monto;
        return true;
    }


}