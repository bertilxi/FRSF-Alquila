/**
 * Copyright (C) 2016 Fernando Berti - Daniel Campodonico - Emiliano Gioria - Lucas Moretti - Esteban Rebechi - Andres Leonel Rico
 * This file is part of Olimpo.
 *
 * Olimpo is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Olimpo is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Olimpo. If not, see <http://www.gnu.org/licenses/>.
 */
package app.comun;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.springframework.stereotype.Service;

/**
 * Encargada de la encriptación de contraseñas
 */
@Service
public class EncriptadorPassword {

	private final static Integer ITERATIONS = 10000;
	private final static Integer KEY_LENGTH = 256;
	private final static String SAL_GLOBAL = "G��vh�{|Xз�m�Ũ`h";

	/**
	 * Encripta un String con el algoritmo MD5.
	 *
	 * @param palabra
	 *            palabra a encriptar, se borrará al terminar.
	 * @param sal
	 *            sal para ocultar la palabra.
	 * @return String
	 */
	public String encriptar(char[] palabra, String sal) {
		return new String(hashPassword(palabra, (sal + SAL_GLOBAL).getBytes(), ITERATIONS, KEY_LENGTH));
	}

	private byte[] hashPassword(final char[] password, final byte[] salt, final int iterations, final int keyLength) {
		try{
			SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
			PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, keyLength);
			SecretKey key = skf.generateSecret(spec);
			byte[] res = key.getEncoded();
			Arrays.fill(password, '\u0000'); // clear sensitive data
			return res;
		} catch(NoSuchAlgorithmException | InvalidKeySpecException e){
			throw new RuntimeException(e);
		}
	}

	public String generarSal() {
		SecureRandom random = new SecureRandom();
		byte bytes[] = new byte[20];
		random.nextBytes(bytes);
		return new String(bytes);
	}
}
