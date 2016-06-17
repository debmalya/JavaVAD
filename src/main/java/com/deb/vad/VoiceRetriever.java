package com.deb.vad;

public class VoiceRetriever implements Runnable {
	
	private SoundCache cache = null;
	
	public VoiceRetriever(SoundCache soundMap){
		cache = soundMap;
	}

	@Override
	public void run() {
		
		while (true) {
			byte[] values = cache.getAudioMap()
					.get(System.currentTimeMillis() - 3000);
			if (values != null) {
				System.out.println("R ");
//				System.out.print(values.length);
			} else {
//				System.out.println("N.V.");
			}
		}
	}

}
