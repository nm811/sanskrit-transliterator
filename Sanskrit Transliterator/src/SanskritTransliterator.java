import java.util.*;
import java.io.*;

public class SanskritTransliterator {
	static HashMap<Character, String> vowels, maatraas, consonants, other, numbers, punctuation;
	static HashSet<Character> semivowels, gutturals, palatals, cerebrals, dentals, labials, nasals, fricatives;
	static HashMap<Character, String> initializeCharacters(char[] devanagari, String[] roman) {
		HashMap<Character, String> map = new HashMap<Character, String>();
		for (int i = 0; i < devanagari.length; i++) {
			map.put(devanagari[i], roman[i]);
		}
		return map;
	}
	static HashSet<Character> groupCharacters(char[] devanagari) {
		HashSet<Character> set = new HashSet<Character>();
		for (int i = 0; i < devanagari.length; i++) {
			set.add(devanagari[i]);
		}
		return set;
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		char[] devanagari = {'अ', 'आ', 'इ', 'ई', 'उ', 'ऊ', 'ऎ', 'ए', 'ऐ', 'ऒ', 'ओ', 'औ', 'ऋ', 'ॠ', 'ऌ', 'ॡ'};
		String[] roman = {"a", "ā", "i", "ī", "u", "ū", "e", "ē", "ai", "o", "ō", "au", "r̥", "r̥̄", "l̥", "l̥̄"};
		vowels = initializeCharacters(devanagari, roman);
		
		// 'ॲ', 'ऍ', 'ऑ'
		// "ê", "ê", "ô"
		
		devanagari = new char[]{'ा', 'ि', 'ी', 'ु', 'ू', 'ॆ', 'े', 'ै', 'ॊ', 'ो', 'ौ', 'ृ', 'ॄ', 'ॢ', 'ॣ', 'ः', 'ँ'};
		roman = new String[]{"ā", "i", "ī", "u", "ū", "e", "ē", "ai", "o", "ō", "au", "r̥", "r̥̄", "l̥", "l̥̄", "ḥ", "m̐"};
		maatraas = initializeCharacters(devanagari, roman);
		
		devanagari = new char[]{'क', 'ख', 'ग', 'घ', 'ङ', 'च', 'छ', 'ज', 'झ', 'ञ', 'ट', 'ठ', 'ड', 'ढ', 'ण', 'त', 'थ', 'द', 'ध', 'न', 'प', 'फ', 'ब', 'भ', 'म', 'य', 'र', 'ल', 'ळ', 'व', 'श', 'ष', 'स', 'ह'};
		roman = new String[]{"ka", "kha", "ga", "gha", "ṅa", "ca", "cha", "ja", "jha", "ña", "ṭa", "ṭha", "ḍa", "ḍha", "ṇa", "ta", "tha", "da", "dha", "na", "pa", "pha", "ba", "bha", "ma", "ya", "ra", "la", "ḷa", "va", "śa", "ṣa", "sa", "ha"};
		consonants = initializeCharacters(devanagari, roman);
		
		devanagari = new char[]{'क़', 'ख़', 'ग़', 'ज़', 'ड़', 'ढ़', 'फ़', 'ऴ'};
		roman = new String[]{"qa", "k͟ha", "ġa", "za", "ṛa", "ṛha", "fa", "ḻ"};
		other = initializeCharacters(devanagari, roman);
		
		devanagari = new char[]{'०', '१', '२', '३', '४', '५', '६', '७', '८', '९', 'ऽ', 'ॐ'};
		roman = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "\'", "ōṃ"};
		numbers = initializeCharacters(devanagari, roman);
		
		devanagari = new char[]{'।', '॥', 'ॽ'};
		roman = new String[]{"।", "॥", "ॽ"};
		punctuation = initializeCharacters(devanagari, roman);
		
		devanagari = new char[]{'प', 'फ', 'ब', 'भ', 'म'};
		labials = groupCharacters(devanagari);
		
		devanagari = new char[]{'य', 'र', 'ल', 'व'};
		semivowels = groupCharacters(devanagari);
		
		devanagari = new char[]{'क', 'ख', 'ग', 'घ', 'ङ'};
		gutturals = groupCharacters(devanagari);
		
		devanagari = new char[]{'च', 'छ', 'ज', 'झ', 'ञ'};
		palatals = groupCharacters(devanagari);
		
		devanagari = new char[]{'ट', 'ठ', 'ड', 'ढ', 'ण', 'ळ'};
		cerebrals = groupCharacters(devanagari);
		
		devanagari = new char[]{'त', 'थ', 'द', 'ध', 'न'};
		dentals = groupCharacters(devanagari);
		
		devanagari = new char[]{'स', 'ह', 'श', 'ष'};
		fricatives = groupCharacters(devanagari);
		
		StringBuilder message = new StringBuilder();
		String line = br.readLine();
		while (line != "") {
		//for (String line = br.readLine(); line != null; line = br.readLine()) {
			//st = new StringTokenizer(br.readLine());
			st = new StringTokenizer(line);
			while (st.hasMoreTokens()) {
				String s = st.nextToken();
				//System.out.println("s = " + s);
				
				StringBuilder word = new StringBuilder(s.length());
				//boolean invalid = false;
				for (int i = 0; i < s.length(); i++) {
					char c = s.charAt(i);
					//String c = String.valueOf(s.charAt(i));
					if (s.charAt(i) == 'ं') {
						if (i + 1 >= s.length()) { // the anusvara is the last character
							word.append('ṃ');
						} else if (vowels.containsKey(s.charAt(i + 1)) || maatraas.containsKey(s.charAt(i + 1)) || semivowels.contains(s.charAt(i + 1)) || labials.contains(s.charAt(i + 1)) || fricatives.contains(s.charAt(i + 1))) { // is the fricatives part correct?
							word.append('ṃ');
						} else if (gutturals.contains(s.charAt(i + 1))) {
							word.append('ṅ');
						} else if (palatals.contains(s.charAt(i + 1))) {
							word.append('ñ');
						} else if (cerebrals.contains(s.charAt(i + 1))) {
							word.append('ṇ');
						} else if (dentals.contains(s.charAt(i + 1))) {
							word.append('n');
						} else { // for invalid character
							word.append('ṃ');
						}
					} else if (s.charAt(i) == '्') {
						if (word.length() - 1 >= 0) {
							if (word.charAt(word.length() - 1) == 'a') {
								if (i - 1 >= 0 && s.charAt(i - 1) != 'अ') { // if the current character is not the first character and the previous character was not a 'अ'
									word.deleteCharAt(word.length() - 1); // cut the previous letter
								}
							} // else there are no characters to remove, so do nothing
						} // else there are no characters to remove, so do nothing
					} else if (maatraas.containsKey(c)) {
						if (c != 'ः' && c != 'ँ') {
							if (word.length() - 1 >= 0) {
								if (word.charAt(word.length() - 1) == 'a') {
									if (i - 1 >= 0 && s.charAt(i - 1) != 'अ') { // if the current character is not the first character and the previous character was not a 'अ'
										word.deleteCharAt(word.length() - 1); // cut the previous letter
									}
								} // else there are no characters to replace
							} // else there are no characters to replace
						}
						word.append(maatraas.get(c));
					} else if (consonants.containsKey(c)) {
						word.append(consonants.get(c));
					} else if (vowels.containsKey(c)) {
						word.append(vowels.get(c));
					} else if (numbers.containsKey(c)) {
						word.append(numbers.get(c));
					} else if (punctuation.containsKey(c)) {
						word.append(punctuation.get(c));
					} else {
						word.append(c);
					}
				}
				message.append(word);
				if (st.hasMoreTokens()) {
					message.append(' ');
				}
			}
			message.append("\n");
			line = br.readLine();
		}
		System.out.println("---------------------------------------\nmessage:\n");
		System.out.println(message.toString());
	}
}
