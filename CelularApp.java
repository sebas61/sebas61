import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CelularApp extends JFrame {
    private JTextField tfAlmacenamiento, tfPixeles, tfProcesador, tfColor, tfMarca;
    private String almacenamiento, pixeles, procesador, color, marca;
    private JTextArea historialLlamadas, historialMensajes;
    private JTextField displayCalculadora;
    private double primerNumero = 0;
    private String operador = "";
    private ArrayList<String> llamadasHistorial = new ArrayList<>();
    private ArrayList<String> mensajesHistorial = new ArrayList<>();

    public CelularApp() {
        mostrarPantallaEspecificaciones();
    }

    private void mostrarPantallaEspecificaciones() {
        setTitle("Especificaciones del Dispositivo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLayout(new GridLayout(6, 2));

        tfAlmacenamiento = new JTextField();
        tfPixeles = new JTextField();
        tfProcesador = new JTextField();
        tfColor = new JTextField();
        tfMarca = new JTextField();

        add(new JLabel("Almacenamiento (GB):"));
        add(tfAlmacenamiento);
        add(new JLabel("Pixeles de Cámara (MP):"));
        add(tfPixeles);
        add(new JLabel("Procesador:"));
        add(tfProcesador);
        add(new JLabel("Color:"));
        add(tfColor);
        add(new JLabel("Marca:"));
        add(tfMarca);

        JButton btnAceptar = new JButton("Aceptar");
        btnAceptar.addActionListener(e -> {
            if (validarCampos()) {
                almacenamiento = tfAlmacenamiento.getText();
                pixeles = tfPixeles.getText();
                procesador = tfProcesador.getText();
                color = tfColor.getText();
                marca = tfMarca.getText();
                mostrarMenuPrincipal();
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, completa todos los campos correctamente.");
            }
        });

        add(new JLabel());
        add(btnAceptar);

        setVisible(true);
    }

    private boolean validarCampos() {
        try {
            if (tfAlmacenamiento.getText().isEmpty() || tfPixeles.getText().isEmpty() ||
                tfProcesador.getText().isEmpty() || tfColor.getText().isEmpty() || tfMarca.getText().isEmpty()) {
                return false;
            }
            Integer.parseInt(tfAlmacenamiento.getText());
            Integer.parseInt(tfPixeles.getText());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void mostrarMenuPrincipal() {
        getContentPane().removeAll();
        setTitle("Menú Principal del Celular");
        setLayout(null);
        setSize(400, 400);

        JButton btnCalculadora = new JButton("Calculadora");
        JButton btnLlamadas = new JButton("Llamadas");
        JButton btnMensajes = new JButton("Mensajes");
        JButton btnInfo = new JButton("Información");
        JButton btnApagar = new JButton("Apagar");

        btnCalculadora.setBounds(50, 50, 150, 50);
        btnLlamadas.setBounds(200, 50, 150, 50);
        btnMensajes.setBounds(50, 120, 150, 50);
        btnInfo.setBounds(200, 120, 150, 50);
        btnApagar.setBounds(125, 200, 150, 50);

        add(btnCalculadora);
        add(btnLlamadas);
        add(btnMensajes);
        add(btnInfo);
        add(btnApagar);

        btnCalculadora.addActionListener(e -> mostrarCalculadora());
        btnLlamadas.addActionListener(e -> mostrarLlamadas());
        btnMensajes.addActionListener(e -> mostrarMensajes());
        btnInfo.addActionListener(e -> mostrarInformacion());
        btnApagar.addActionListener(e -> System.exit(0));

        revalidate();
        repaint();
    }

    private void mostrarCalculadora() {
        getContentPane().removeAll();
        setTitle("Calculadora");
        setLayout(new BorderLayout());

        displayCalculadora = new JTextField();
        displayCalculadora.setEditable(false);
        displayCalculadora.setHorizontalAlignment(JTextField.RIGHT);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 4));

        String[] botones = {
            "7", "8", "9", "+",
            "4", "5", "6", "-",
            "1", "2", "3", "*",
            "C", "0", "=", "/",
            "√", "^", "Volver"
        };

        for (String texto : botones) {
            JButton btn = new JButton(texto);
            btn.addActionListener(e -> manejarEntradaCalculadora(e.getActionCommand()));
            panel.add(btn);
        }

        add(displayCalculadora, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);

        revalidate();
        repaint();
    }

    private void manejarEntradaCalculadora(String comando) {
        switch (comando) {
            case "C":
                displayCalculadora.setText("");
                primerNumero = 0;
                operador = "";
                break;
            case "+":
            case "-":
            case "*":
            case "/":
            case "^":
                operador = comando;
                primerNumero = Double.parseDouble(displayCalculadora.getText());
                displayCalculadora.setText("");
                break;
            case "=":
                try {
                    double segundoNumero = Double.parseDouble(displayCalculadora.getText());
                    double resultado = realizarOperacion(primerNumero, segundoNumero, operador);
                    displayCalculadora.setText(String.valueOf(resultado));
                } catch (Exception e) {
                    displayCalculadora.setText("Error");
                }
                break;
            case "Volver":
                mostrarMenuPrincipal();
                break;
            default:
                displayCalculadora.setText(displayCalculadora.getText() + comando);
        }
    }

    private double realizarOperacion(double num1, double num2, String operador) {
        switch (operador) {
            case "+":
                return num1 + num2;
            case "-":
                return num1 - num2;
            case "*":
                return num1 * num2;
            case "/":
                if (num2 != 0) return num1 / num2;
                else throw new ArithmeticException("División por cero");
            case "^":
                return Math.pow(num1, num2);
            default:
                throw new UnsupportedOperationException("Operador no soportado");
        }
    }

    private void mostrarMensajes() {
        getContentPane().removeAll();
        setTitle("Mensajes");
        setLayout(new BorderLayout());

        JPanel panelSuperior = new JPanel(new GridLayout(3, 2));
        JTextField tfNumero = new JTextField();
        JTextField tfMensaje = new JTextField();
        JTextArea historial = new JTextArea();

        panelSuperior.add(new JLabel("Número:"));
        panelSuperior.add(tfNumero);
        panelSuperior.add(new JLabel("Mensaje:"));
        panelSuperior.add(tfMensaje);
        JButton btnEnviar = new JButton("Enviar");
        JButton btnVolver = new JButton("Volver");
        panelSuperior.add(btnEnviar);
        panelSuperior.add(btnVolver);

        add(panelSuperior, BorderLayout.NORTH);
        add(new JScrollPane(historial), BorderLayout.CENTER);

        btnEnviar.addActionListener(e -> {
            String numero = tfNumero.getText();
            String mensaje = tfMensaje.getText();
            if (!numero.isEmpty() && !mensaje.isEmpty()) {
                mensajesHistorial.add("A " + numero + ": " + mensaje);
                historial.setText(String.join("\n", mensajesHistorial));
                tfNumero.setText("");
                tfMensaje.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, completa ambos campos.");
            }
        });

        btnVolver.addActionListener(e -> mostrarMenuPrincipal());

        revalidate();
        repaint();
    }

    private void mostrarLlamadas() {
        getContentPane().removeAll();
        setTitle("Llamadas");
        setLayout(new BorderLayout());

        JPanel panelSuperior = new JPanel(new GridLayout(2, 2));
        JTextField tfNumero = new JTextField();
        JTextArea historial = new JTextArea();

        panelSuperior.add(new JLabel("Número:"));
        panelSuperior.add(tfNumero);
        JButton btnLlamar = new JButton("Llamar");
        JButton btnVolver = new JButton("Volver");
        panelSuperior.add(btnLlamar);
        panelSuperior.add(btnVolver);

        add(panelSuperior, BorderLayout.NORTH);
        add(new JScrollPane(historial), BorderLayout.CENTER);

        btnLlamar.addActionListener(e -> {
            String numero = tfNumero.getText();
            if (!numero.isEmpty()) { if (llamadasHistorial.size() < 10) { 
            llamadasHistorial.add(numero); 
            historial.setText(String.join("\n", llamadasHistorial)); 
            tfNumero.setText(""); } 
            else { JOptionPane.showMessageDialog(this, "Solo se pueden ingresar hasta 10 números."); 
        } 
     } else { JOptionPane.showMessageDialog(this, "Por favor, ingresa un número."); } });

        btnVolver.addActionListener(e -> mostrarMenuPrincipal());

        revalidate();
        repaint();
    }

    private void mostrarInformacion() {
        getContentPane().removeAll();
        setTitle("Información del Dispositivo");
        setLayout(new GridLayout(6, 1));

        add(new JLabel("Almacenamiento: " + almacenamiento + " GB"));
        add(new JLabel("Pixeles de Cámara: " + pixeles + " MP"));
        add(new JLabel("Procesador: " + procesador));
        add(new JLabel("Color: " + color));
        add(new JLabel("Marca: " + marca));

        JButton btnVolver = new JButton("Volver");
        btnVolver.addActionListener(e -> mostrarMenuPrincipal());
        add(btnVolver);

        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CelularApp::new);
    }
}
