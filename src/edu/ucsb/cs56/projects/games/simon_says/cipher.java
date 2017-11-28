package edu.ucsb.cs56.projects.games.simon_says;

public class cipher {

  public static final String LETTERS_NUMBERS = "abcdefghijklmnopqrstuvwxyz0123456789: ";

  public static String encrypt(String text, int shiftby){
    text = text.toLowerCase();
    String ciphered = "";
    for (int i = 0; i < text.length(); i++){
      int position = LETTERS_NUMBERS.indexOf(text.charAt(i));
      int key_value = (shiftby + position) % 38;
      char replace_value = LETTERS_NUMBERS.charAt(key_value);
      ciphered += replace_value;
    }
    return ciphered;
  }

  public static String decrypt(String ciphered, int shiftby){
    ciphered = ciphered.toLowerCase();
    String text = "";
    for (int i = 0; i < ciphered.length(); i++){
      int position = LETTERS_NUMBERS.indexOf(ciphered.charAt(i));
      int key_value = (position - shiftby) % 38;
      if (key_value < 0){
        key_value = LETTERS_NUMBERS.length() + key_value;
      }
      char replace_value = LETTERS_NUMBERS.charAt(key_value);
      text += replace_value;
    }
    return text;
  }
}
