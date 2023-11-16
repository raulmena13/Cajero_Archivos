import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;

class Administrador {
    public void mostrarRegistros() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("logs.txt"));
            String accion;
            System.out.println("Acciones:");
            while ((accion = br.readLine()) != null) {
                System.out.println(accion);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void mostrardisponibilidadDeBilletes(Cajero cajero) {
        System.out.println("Cantidad de billetes disponibles:");
        for (int billete : cajero.disponibilidadDeBilletes.keySet()) {
            int cantidad = cajero.disponibilidadDeBilletes.get(billete);
            System.out.println("$" + billete + ": " + cantidad);
        }
    }
}
