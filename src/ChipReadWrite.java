import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

public class ChipReadWrite {
	public static void main(String[] args) {
		ChipReadWrite chipReadWrite = new ChipReadWrite();
		chipReadWrite.writeChips(200);
		long chip = chipReadWrite.readChips();
		System.out.println(chip);
	}

	long readChips() {
		// Read chips from file
		File chipFile = new File("DCC.chips");
		String formatVersion;
		String[] readLines;
		try {
			formatVersion = new String(Files.readAllBytes(chipFile.toPath()));
			formatVersion = dequantomizeString(formatVersion);
			readLines = formatVersion.split("\n");
		} catch (IOException e) {
			return 0;
		}
		switch (readLines[0]) {
			case "DCCFormatVersion=1.0":
				return formatVersion1_0(readLines);
			case "DCCFormatVersion=1.1":
				break;
			default:
				System.out.println("Invalid file format");
				break;
		}
		return 0;
	}

	void writeChips(long chips) {
		// Write chips to file
		File chipFile = new File("DCC.chips");
		FileWriter chipWriter;
		try {
			chipWriter = new FileWriter(chipFile);
			String writeValue = "";
			writeValue += "DCCFormatVersion=1.0\n";
			writeValue += "Chips1=" + chips + "\n";
			writeValue += "Chips2=" + chips + "\n";
			writeValue += "Chips3=" + chips + "\n";
			chipWriter.write(quantomizeString(writeValue));
			chipWriter.close();
		} catch (IOException ignored) {
		}
	}

	private long formatVersion1_0(String[] readLines) {
		// Format version 1.0
		final long chips1 = Long.parseLong(readLines[1].split("=")[1]);
		final long chips2 = Long.parseLong(readLines[2].split("=")[1]);
		final long chips3 = Long.parseLong(readLines[3].split("=")[1]);

		if (chips1 == chips2 && chips2 == chips3) {
			return chips1;
		} else {
			System.out.println("Invalid file format");
			return 0;
		}
	}

	private String quantomizeString(String input) {
		// Quantomize string
		for (int i = 0; i < input.length(); i++) {
			input = input.substring(0, i) + (char) (input.charAt(i) + i) + input.substring(i + 1);
		}
		return input;
	}

	private String dequantomizeString(String input) {
		// Dequantomize string
		for (int i = 0; i < input.length(); i++) {
			input = input.substring(0, i) + (char) (input.charAt(i) - i) + input.substring(i + 1);
		}
		return input;
	}
}
