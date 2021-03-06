import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.Semaphore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RaceCondition {
	
	private static Logger logger = LoggerFactory.getLogger(RaceCondition.class);

	private final Semaphore semaphore;

	public RaceCondition(int count) {
		this.semaphore = new Semaphore(count);
	}

	public void improveFileWrite(int value) throws InterruptedException {
		semaphore.acquire();
		try {
			fileWriter(value);
		} finally {
			semaphore.release();
		}
	}

	public void improveFileRead() throws InterruptedException {
		semaphore.acquire();
		try {
			fileReader();
		} finally {
			semaphore.release();
		}
	}

	public void fileWriter(int value) {
		BufferedWriter file = null;
		FileWriter writer = null;
		try {
			logger.info("Write : " + value);
			writer = new FileWriter("input.txt", true);
			file = new BufferedWriter(writer);
			file.write("\n" + value);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				file.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public String fileReader() {
		BufferedReader file = null;
		FileReader reader = null;

		try {
			reader = new FileReader("input.txt");
			file = new BufferedReader(reader);

			String line = null;
			String last = null;

			while ((line = file.readLine()) != null) {
				last = line;
			}

			return last;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				file.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
