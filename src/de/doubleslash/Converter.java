package de.doubleslash;


import java.util.Arrays;
import java.util.Base64;
import java.nio.charset.StandardCharsets;
import java.io.UnsupportedEncodingException;


public class Converter {


    private byte[] data;

    private Converter (byte[] data) {
        this.data = data;
    }


    public static Converter fromUtf8String (String utf8String) {
        byte[] bytes = utf8String.getBytes();
        return new Converter(bytes);
    }

    public static Converter fromBase64String (String base64String) {
        byte[] bytes = Base64.getDecoder().decode(base64String);
        return new Converter(bytes);
    }

    public static Converter fromDecimalString (String decimalString) {
        String[] parts = decimalString.split(",");
        byte[] bytes = new byte[parts.length];
        for (int i = 0; i < parts.length; i++) {
            bytes[i] = (byte) Integer.parseInt(parts[i]);
        }
        return new Converter(bytes);
    }

    public static Converter fromHexadecimalString (String hexadecimalString) {
        String[] parts = hexadecimalString.split(",");
        byte[] bytes = new byte[parts.length];
        for (int i = 0; i < parts.length; i++) {
            bytes[i] = (byte) Integer.parseInt(parts[i], 16);
        }
        return new Converter(bytes);
    }

    public static Converter fromBinaryString (String binaryString) {
        String[] parts = binaryString.split(",");
        byte[] bytes = new byte[parts.length];
        for (int i = 0; i < parts.length; i++) {
            bytes[i] = (byte) Integer.parseInt(parts[i], 2);
        }
        return new Converter(bytes);
    }


    public String toUtf8String () throws UnsupportedEncodingException {
        return new String(data, StandardCharsets.UTF_8.name());
    }

    public String toBase64String () {
        return Base64.getEncoder().encodeToString(data);
    }

    public String toDecimalString () {
        return Arrays.toString(data)
                .substring(1, Arrays.toString(data).length() - 1)
                .replaceAll(" ", "");
    }

    public String toHexadecimalString () {
        StringBuilder str = new StringBuilder();
        for (byte part : Arrays.copyOfRange(data, 0, data.length - 1)) {
            str.append(Integer.toHexString(part)).append(",");
        }
        str.append(Integer.toHexString(data[data.length - 1]));
        return str.toString();
    }

    public String toBinaryString () {
        StringBuilder str = new StringBuilder();
        for (byte part : Arrays.copyOfRange(data, 0, data.length - 1)) {
            str.append(String.format("%8s", Integer.toBinaryString(part)).replace(' ', '0')).append(",");
        }
        str.append(String.format("%8s", Integer.toBinaryString(data[data.length - 1])).replace(' ', '0'));
        return str.toString();
    }
}