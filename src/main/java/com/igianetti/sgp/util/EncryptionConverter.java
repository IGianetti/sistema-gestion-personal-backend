package com.igianetti.sgp.util;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;

@Converter
public class EncryptionConverter implements AttributeConverter<String, String> {

    // !! IMPORTANTE: En producción, esta clave debe ser cargada de forma segura (ej. Vault, KMS) !!
    private static final String ALGORITHM = "AES/ECB/PKCS5Padding";
    private static final byte[] KEY_BYTES = "mi-clave-secreta-de-32-bytes-12345".getBytes(); // 32 bytes = 256 bits

    private final Key key;
    private final Cipher cipher;

    public EncryptionConverter() throws Exception {
        key = new SecretKeySpec(KEY_BYTES, "AES");
        cipher = Cipher.getInstance(ALGORITHM);
    }

    @Override
    public String convertToDatabaseColumn(String attribute) {
        if (attribute == null) return null;
        try {
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return Base64.getEncoder().encodeToString(cipher.doFinal(attribute.getBytes()));
        } catch (Exception e) {
            throw new RuntimeException("Error al cifrar el dato", e);
        }
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;
        try {
            cipher.init(Cipher.DECRYPT_MODE, key);
            return new String(cipher.doFinal(Base64.getDecoder().decode(dbData)));
        } catch (Exception e) {
            // En caso de error (ej. dato corrupto), podemos retornar nulo o lanzar excepción
            // Por ahora, lanzamos excepción para trazabilidad:
            throw new RuntimeException("Error al descifrar el dato", e);
        }
    }
}
