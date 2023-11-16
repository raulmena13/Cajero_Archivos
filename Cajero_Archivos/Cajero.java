import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cajero {
    Map<Integer, Integer> disponibilidadDeBilletes = new HashMap<>();

    public Cajero() {
        cargaDeBilletes();
    }

    public boolean entregarDinero(int cantidad) {
        for (int billete : disponibilidadDeBilletes.keySet()) {
            int cantidadBilletes = disponibilidadDeBilletes.get(billete);
            if (cantidad >= billete) {
                int cantidadNecesaria = cantidad / billete;
                if (cantidadNecesaria <= cantidadBilletes) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean actualizarBilletes(int cantidadRetiro) {
        List<Integer> denominaciones = new ArrayList<>(disponibilidadDeBilletes.keySet());
        Collections.sort(denominaciones, Collections.reverseOrder());

        for (int billete : denominaciones) {
            int cantidadBilletes = disponibilidadDeBilletes.get(billete);
            int cantidadNecesaria = cantidadRetiro / billete;
            int cantidadEntregada = Math.min(cantidadBilletes, cantidadNecesaria);
            cantidadRetiro -= cantidadEntregada * billete;
            disponibilidadDeBilletes.put(billete, cantidadBilletes - cantidadEntregada);
        }
        guardarBilletes();
        return true;
    }

    public void cargaDeBilletes() {
        try {
            FileInputStream fileInputStream = new FileInputStream("billetes.dat");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            disponibilidadDeBilletes = (Map<Integer, Integer>) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            iniciarBilletes();
        }
    }

    public void guardarBilletes() {
        try {
            FileOutputStream fos = new FileOutputStream("billetes.dat");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(disponibilidadDeBilletes);
            oos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void iniciarBilletes() {
        disponibilidadDeBilletes.put(100, 100);
        disponibilidadDeBilletes.put(200, 100);
        disponibilidadDeBilletes.put(500, 20);
        disponibilidadDeBilletes.put(1000, 10);
    }

    
}