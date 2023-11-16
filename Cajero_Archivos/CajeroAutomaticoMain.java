import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class CajeroAutomaticoMain {

static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.print("Ingrese su nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese su NIP de 4 dígitos: ");
        int nip = scanner.nextInt();

        if (nombre.equalsIgnoreCase("admin") && nip == 3243) {
            modoAdministrador();
        } else {
            Usuario usuario = new Usuario(nombre, nip);
            modoCajero(usuario);
        }
    }

    public static void modoCajero(Usuario usuario) {
        Cajero cajero = new Cajero();

        System.out.println("Modo Cajero");
        while (true) {
            System.out.println("Saldo disponible: $" + usuario.getSaldo());
            System.out.println("Monto mínimo a retirar: $100");
            System.out.println("Seleccione una opción:");
            System.out.println("1. Consultar saldo");
            System.out.println("2. Retirar efectivo");
            System.out.println("3. Salir del cajero");
            int opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    System.out.println(usuario.getSaldo());
                    break;
                case 2:
                    retirarEfectivo(usuario, cajero);
                    break;
                case 3:
                    cajero.guardarBilletes();
                    guardarRegistro("Salir", usuario.getNombre(), usuario.getSaldo(), "SI");
                    System.out.println("Hasta luego, Vuelva pronto");
                    return;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    public static void retirarEfectivo(Usuario usuario, Cajero cajero) {
        System.out.print("Cantidad que desea retirar: $");
        int cantidadRetiro = scanner.nextInt();

        if (cantidadRetiro < 100 || cantidadRetiro > usuario.getSaldo()) {
            System.out.println("Cantidad no válida. Debe ser mayor o igual a $100 y no exceder su saldo actual.");
            guardarRegistro("Retirar", usuario.getNombre(), cantidadRetiro, "NO");
        } else {
            if (cajero.entregarDinero(cantidadRetiro)) {
                if (usuario.retirarEfectivo(cantidadRetiro)) {
                    if (cajero.actualizarBilletes(cantidadRetiro)) {
                        System.out.println("Retiro exitoso. Se han entregado billetes de $" + cantidadRetiro);
                        guardarRegistro("Retirar", usuario.getNombre(), cantidadRetiro, "SI");
                    } else {
                        System.out.println("No se pudieron entregar los billetes. Por favor, intente con otra cantidad.");
                        guardarRegistro("Retirar", usuario.getNombre(), cantidadRetiro, "NO");
                    }
                }
            } else {
                System.out.println("No hay suficientes billetes para el retiro. Por favor, intente con otra cantidad.");
                guardarRegistro("Retirar", usuario.getNombre(), cantidadRetiro, "NO");
            }
        }
    }

    public static void guardarRegistro(String accion, String usuario, int cantidad, String seRealizo) {
        try {
            FileWriter fw = new FileWriter("logs.txt", true);
            PrintWriter pw = new PrintWriter(fw);
            pw.println(accion + ", " + usuario + ", " + cantidad + ", " + seRealizo);
            pw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void modoAdministrador() {
        Administrador admin = new Administrador();
        Cajero cajero = new Cajero();

        System.out.println("Modo Administrador");
        while (true) {
            System.out.println("Seleccione una opción:");
            System.out.println("1. Mostrar registros de acciones");
            System.out.println("2. Mostrar cantidad de billetes disponibles");
            System.out.println("3. Salir del modo administrador");
            int opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    admin.mostrarRegistros();
                    break;
                case 2:
                    admin.mostrardisponibilidadDeBilletes(cajero);
                    break;
                case 3:
                    System.out.println("Saliendo del modo administrador.");
                    return;
                default:
                    System.out.println("Opción no válida. Por favor, seleccione una opción válida.");
            }
        }
    }
}