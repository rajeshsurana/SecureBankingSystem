package com.spring.controllers;

import java.io.IOException;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.crypto.Cipher;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.model.BankUser;
import com.spring.model.NewBankUser;
import com.spring.model.NewBankUserList;
import com.spring.model.PkiVerification;
import com.spring.model.StringList;
import com.spring.service.BankUserService;
import com.spring.service.EmailService;
import com.spring.service.NewBankUserService;
import com.spring.service.PkiVerificationService;
import com.spring.util.StaticConstants;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

@SuppressWarnings("restriction")
@Controller
public class PKIController {

	private static final String alphabet = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

	private Random rand;
	private int length;

	private PrivateKey privatekey;
	private PublicKey publickey;
	private EmailService emailService;
	private BankUserService bankUserService;
	private NewBankUserService newBankService;

	public NewBankUserService getNewBankService() {
		return newBankService;
	}

	@Autowired
	public void setNewBankService(NewBankUserService newBankService) {
		this.newBankService = newBankService;
	}

	private PkiVerificationService pkiverificationService;

	public PkiVerificationService getPkiverificationService() {
		return pkiverificationService;
	}

	@Autowired
	public void setPkiverificationService(PkiVerificationService pkiverificationService) {
		this.pkiverificationService = pkiverificationService;
	}

	public PKIController() {
		this.rand = new Random();
		this.length = 20;
	}

	public BankUserService getBankUserService() {
		return bankUserService;
	}

	@Autowired
	public void setBankUserService(BankUserService bankUserService) {
		this.bankUserService = bankUserService;
	}

	public EmailService getEmailService() {
		return emailService;
	}

	@Autowired
	public void setEmailService(EmailService emailService) {
		this.emailService = emailService;
	}

	@RequestMapping(value = "/generatePKI.do", method = RequestMethod.POST)
	public String generatePKI(HttpServletRequest request) {
		try {
			String UserName = (String) request.getSession().getAttribute("sUserName");
			System.out.println("-------GENRATE PUBLIC and PRIVATE KEY-------------");
			KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
			generator.initialize(1024);
			KeyPair keypair = generator.generateKeyPair();
			privatekey = keypair.getPrivate();
			publickey = keypair.getPublic();
			BASE64Encoder base64encode = new BASE64Encoder();
			BASE64Decoder base64decode = new BASE64Decoder();

			System.out.println("Public Key - " + publickey);
			System.out.println("Private Key - " + privatekey);

			System.out.println("\n------- PULLING OUT PARAMETERS WHICH MAKES KEYPAIR----------\n");
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			RSAPublicKeySpec rsaPubKeySpec = keyFactory.getKeySpec(publickey, RSAPublicKeySpec.class);
			RSAPrivateKeySpec rsaPrivKeySpec = keyFactory.getKeySpec(privatekey, RSAPrivateKeySpec.class);
			System.out.println("PubKey Modulus : " + rsaPubKeySpec.getModulus());
			System.out.println("PubKey Exponent : " + rsaPubKeySpec.getPublicExponent());
			System.out.println("PrivKey Modulus and Exponent : ");
			String toEncode = rsaPrivKeySpec.getModulus() + "|" + rsaPrivKeySpec.getPrivateExponent();
			System.out.println("Value to be encode :::" + toEncode);
			System.out.println("PUBLIC KEY MODULUS AND EXPONENT");
			String publickeyServer = rsaPubKeySpec.getModulus() + "|" + rsaPubKeySpec.getPublicExponent();
			// encode data on your side using BASE64
			byte[] PublicKEYbytesEncoded = Base64.encodeBase64(publickeyServer.getBytes());
			System.out.println("ecncoded value is " + new String(PublicKEYbytesEncoded));

			// encode data on your side using BASE64
			byte[] bytesEncoded = Base64.encodeBase64(toEncode.getBytes());
			System.out.println("ecncoded value is " + new String(bytesEncoded));

			System.out.println("\n--------SAVING PUBLIC KEY AND PRIVATE KEY TO DATABASE-------\n");
			PkiVerification tempVerification = new PkiVerification();

			tempVerification.setUsername(UserName);
			tempVerification.setUserPublicKeyModulus(rsaPubKeySpec.getModulus().toString());
			tempVerification.setUserPublicKeyExponent(rsaPubKeySpec.getPublicExponent().toString());

			String verificationCode = "";
			for (int i = 0; i < length; i++) {
				verificationCode = verificationCode + alphabet.charAt(rand.nextInt(alphabet.length()));
			}
			tempVerification.setUserVerificationCode(verificationCode);
			if (pkiverificationService.findUserVerificationDetailsByUserName(UserName) == null) {
				pkiverificationService.setPkiVerificationDetails(tempVerification);
			} else {
				pkiverificationService.updatePkiVerificationDetails(tempVerification);
			}

			// Encrypt Data using Public Key
			byte[] encryptedData = encryptData(verificationCode, publickey);

			BankUser tempUser = bankUserService.findUserByUserName(UserName);
			String subject = "Your Private Key For the transaction";
			StringBuilder message = new StringBuilder();

			message.append("Hello " + UserName + ",\n\n");
			message.append("WARNING::: THIS IS HIGHLY CONFIDENTIAL INFORMATION. ");
			message.append("PLEASE ENSURE THAT THIS INFORMATION IS NOT DISCLOSED TO OTHERS !!!\n\n\n");
			message.append("\n\n***************************************************");
			message.append("\n\nYour  encoded private key is \n\n\n ");
			message.append(new String(bytesEncoded));
			message.append("\n\n***************************************************");
			message.append("\n\n AND YOUR CHALLENGE IS\n\n\n " + Base64.encodeBase64String(encryptedData) + "\n\n");
			message.append("\n\n\n***************************************************\n\n");
			message.append("\n\n\n The Servers Public Key is \n\n");
			message.append(StaticConstants.ServerPublicKey);
			message.append("\n\n***************************************************\n\n");
			// prints debug info
			System.out.println("To: " + StaticConstants.fromAddress);
			System.out.println("Subject: " + subject);
			System.out.println("Message: " + message);
			emailService.ReadyToSendEmail(tempUser.getEmail(), StaticConstants.fromAddress, subject,
					message.toString());
			return "VerifyPKI";
		} catch (NoSuchAlgorithmException | InvalidKeySpecException | IOException e) {
			e.printStackTrace();
			return "Error";
		}

	}

	/**
	 * Encrypt Data
	 * 
	 * @param data
	 * @throws IOException
	 */
	private byte[] encryptData(String data, PublicKey pubKey) throws IOException {
		System.out.println("\n----------------ENCRYPTION STARTED------------");

		System.out.println("Data Before Encryption :" + data);
		byte[] dataToEncrypt = data.getBytes();
		byte[] encryptedData = null;
		try {

			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, pubKey);
			encryptedData = cipher.doFinal(dataToEncrypt);
			System.out.println("Encryted Data: " + encryptedData);

		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("----------------ENCRYPTION COMPLETED------------");
		return encryptedData;
	}

	/**
	 * Encrypt Data
	 * 
	 * @param data
	 * @throws IOException
	 */
	private String decryptData(byte[] data, PrivateKey privateKey) throws IOException {
		System.out.println("\n----------------DECRYPTION STARTED------------");
		byte[] descryptedData = null;
		String decryptedData = "";
		try {

			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			descryptedData = cipher.doFinal(data);
			decryptedData = new String(descryptedData);
			System.out.println("Decrypted Data: " + decryptedData);

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("----------------DECRYPTION COMPLETED------------");
		return decryptedData;
	}

	public PublicKey readPublicKeyFromFile(BigInteger modulus, BigInteger exponent) {
		// Get Public Key
		RSAPublicKeySpec rsaPublicKeySpec = new RSAPublicKeySpec(modulus, exponent);
		KeyFactory fact;
		PublicKey publicKey = null;
		try {
			fact = KeyFactory.getInstance("RSA");
			publicKey = fact.generatePublic(rsaPublicKeySpec);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			e.printStackTrace();
		}

		return publicKey;
	}

	public PrivateKey readPrivateKeyFromFile(BigInteger modulus, BigInteger exponent) {
		// Get Private Key
		RSAPrivateKeySpec rsaPrivateKeySpec = new RSAPrivateKeySpec(modulus, exponent);
		KeyFactory fact;
		PrivateKey privateKey = null;
		try {
			fact = KeyFactory.getInstance("RSA");
			privateKey = fact.generatePrivate(rsaPrivateKeySpec);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return privateKey;
	}

	private byte[] string2bytes(String s) {
		int len = s.length();
		byte[] res = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			res[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
		}
		return res;
	}

	@RequestMapping(value = "/verifyPKI.do", method = RequestMethod.POST)
	public ModelAndView verifyPKI(@RequestParam(value = "pki") String pki,
			@RequestParam(value = "message") String message, HttpServletRequest request, Model m,
			final RedirectAttributes redirectAttribute) {

		String userName = (String) request.getSession().getAttribute("sUserName");
		System.out.println("received key " + pki);
		byte[] valueDecoded = Base64.decodeBase64(pki);
		String DecodedValue = new String(valueDecoded);
		byte[] messageDecoded = Base64.decodeBase64(message);
		System.out.println("Decoded value is " + DecodedValue);
		// Decrypt Data using Private Key
		String[] keyComponents = { DecodedValue.substring(0, DecodedValue.indexOf("|")),
				DecodedValue.substring(DecodedValue.indexOf("|") + 1, DecodedValue.length()) };
		String action = "";
		try {
			System.out.println("key :: " + keyComponents[0] + " ::::: \n" + keyComponents[1]);
			BigInteger modulus = new BigInteger(keyComponents[0]);
			BigInteger exponent = new BigInteger(keyComponents[1]);
			privatekey = readPrivateKeyFromFile(modulus, exponent);
			if (privatekey != null) {
				PkiVerification pkiverification = pkiverificationService
						.findUserVerificationDetailsByUserName(userName);
				if (pkiverification != null) {
					if (pkiverification.getUserVerificationCode().equals(decryptData(messageDecoded, privatekey))) {

						return new ModelAndView("PKIDecryptedText", "pkiverification", pkiverification);
					} else {
						ModelAndView mvc = new ModelAndView("VerifyPKI");
						mvc.addObject("message", "Incorrect Value");
						return mvc;
					}
				} else {
					ModelAndView mvc = new ModelAndView("VerifyPKI");
					mvc.addObject("message", "Incorrect Value");
					return mvc;
				}

			} else {
				ModelAndView mvc = new ModelAndView("VerifyPKI");
				mvc.addObject("message", "Incorrect Value");
				return mvc;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ModelAndView("Error");
		}

	}

	@RequestMapping(value = "/encryptPKI.do", method = RequestMethod.POST)
	public ModelAndView verifyPKI(@RequestParam(value = "pki") String pki, HttpServletRequest request, Model m,
			final RedirectAttributes redirectAttribute) {
		String userName = (String) request.getSession().getAttribute("sUserName");
		System.out.println("received key " + pki);
		byte[] valueDecoded = Base64.decodeBase64(pki);
		String DecodedValue = new String(valueDecoded);
		System.out.println("Decoded Value :: " + DecodedValue);
		String[] keyComponents = { DecodedValue.substring(0, DecodedValue.indexOf("|")),
				DecodedValue.substring(DecodedValue.indexOf("|") + 1, DecodedValue.length()) };
		BigInteger modulus = new BigInteger(keyComponents[0]);
		BigInteger exponent = new BigInteger(keyComponents[1]);
		publickey = readPublicKeyFromFile(modulus, exponent);

		PkiVerification pkiverification = pkiverificationService.findUserVerificationDetailsByUserName(userName);
		if (pkiverification != null && (pkiverification.getUsername() != StaticConstants.ServerPrivateKeyUsername
				|| pkiverification.getUsername() != StaticConstants.ServerPublicKeyUsername)) {
			// Encrypt Data using Public Key
			byte[] encryptedData;
			try {
				encryptedData = encryptData(pkiverification.getUserVerificationCode(), publickey);
				m.addAttribute("EncryptedText", Base64.encodeBase64String(encryptedData));
				return new ModelAndView("PKIEncrypt");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			ModelAndView mvc = new ModelAndView("VerifyPKI");
			mvc.addObject("message", "Incorrect Value");
			return mvc;
		}

		return new ModelAndView("Error");
	}

	@RequestMapping(value = "/verifyServer.do", method = RequestMethod.POST)
	public ModelAndView ProccedToAction(@RequestParam(value = "encryptedText") String encryptedText,
			HttpServletRequest request, Model m, final RedirectAttributes redirectAttribute,
			@ModelAttribute("bankuserList") NewBankUserList bankuserList) {
		String userName = (String) request.getSession().getAttribute("sUserName");
		System.out.println("Encrypted Text :: " + encryptedText);
		PkiVerification pkiverification = pkiverificationService.findUserVerificationDetailsByUserName(userName);
		if (pkiverification != null && (pkiverification.getUsername() != StaticConstants.ServerPrivateKeyUsername
				|| pkiverification.getUsername() != StaticConstants.ServerPublicKeyUsername)) {
			try {
				byte[] messageDecoded = Base64.decodeBase64(encryptedText);
				PkiVerification tempPrivateKeyOfServer = pkiverificationService
						.findUserVerificationDetailsByUserName(StaticConstants.ServerPrivateKeyUsername);
				if (tempPrivateKeyOfServer != null) {
					BigInteger privatemodulus = new BigInteger(tempPrivateKeyOfServer.getUserPublicKeyModulus());
					BigInteger privateexponent = new BigInteger(tempPrivateKeyOfServer.getUserPublicKeyExponent());
					privatekey = readPrivateKeyFromFile(privatemodulus, privateexponent);
					if (pkiverification.getUserVerificationCode().equals(decryptData(messageDecoded, privatekey))) {
						{
							BankUser bankuser = bankUserService.findUserByUserName(userName);
							if (bankuser != null && ((bankuser.getRefUserRole().getBankUserRoleName()
									.equals(StaticConstants.Manager))
									|| (bankuser.getRefUserRole().getBankUserRoleName()
											.equals(StaticConstants.InternalEmployee)))) {
								List<NewBankUser> bankUsersForApproval = new ArrayList<NewBankUser>();
								System.out.println("Showing the list of pending Approvals");
								bankUsersForApproval = newBankService
										.findUsersForApproval(StaticConstants.managerPendingApproval);

								if (null != bankUsersForApproval && bankUsersForApproval.size() > 0) {
									bankuserList.setUsers(bankUsersForApproval);
									for (NewBankUser user : bankUsersForApproval) {
										System.out.printf("%s \t %s \n", user.getFirstName(), user.getLastName());
									}
								}
								m.addAttribute("userList", new StringList());
								return new ModelAndView("ListAccountApprovals", "bankuserList", bankuserList);
							} else if (bankuser != null && ((bankuser.getRefUserRole().getBankUserRoleName()
									.equals(StaticConstants.Merchant))
									|| (bankuser.getRefUserRole().getBankUserRoleName()
											.equals(StaticConstants.ExternalCustomer)))) {
								
								ModelAndView mvc = new ModelAndView("deleteMerchantUser");							
								return mvc;	
							}
						}

					} else {
						ModelAndView mvc = new ModelAndView("VerifyPKI");
						mvc.addObject("message", "Incorrect Value");
						return mvc;
					}
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			ModelAndView mvc = new ModelAndView("VerifyPKI");
			mvc.addObject("message", "Incorrect Value");
			return mvc;
		}

		return new ModelAndView("Error");
	}

}
