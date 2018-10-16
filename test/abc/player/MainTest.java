package abc.player;

import org.junit.Test;

/**
 * 
 * Test Main
 * @category no_didit
 */
public class MainTest {
    
    /*
     * Testing strategy for Main.play:
     *   Playing different songs from different files
     *   
     * Remark: Main.main entry point is tested from terminal
     */
    
    @Test
    public void testPlayAbcSong() throws InterruptedException {
        Main.play("sample_abc/abc_song.abc");
        Thread.sleep(15000);
    }
    
    @Test
    public void testPlayFurElise() throws InterruptedException {
        Main.play("sample_abc/fur_elise.abc");
        Thread.sleep(200000);
    }
    
    @Test
    public void testPlayInvention() throws InterruptedException {
        Main.play("sample_abc/invention.abc");
        Thread.sleep(100000);
    }
    
    @Test
    public void testPlayLittleNightMusic() throws InterruptedException {
        Main.play("sample_abc/little_night_music.abc");
        Thread.sleep(45000);
    }
    
    @Test
    public void testPlayPaddy() throws InterruptedException {
        Main.play("sample_abc/paddy.abc");
        Thread.sleep(45000);
    }
    
    @Test
    public void testPlayPiece1() throws InterruptedException {
        Main.play("sample_abc/piece1.abc");
        Thread.sleep(10000);
    }
    
    @Test
    public void testPlayPiece2() throws InterruptedException {
        Main.play("sample_abc/piece2.abc");
        Thread.sleep(10000);
    }
    
    @Test
    public void testPlayPrelude() throws InterruptedException {
        Main.play("sample_abc/prelude.abc");
        Thread.sleep(130000);
    }
    
    @Test
    public void testPlaySample1() throws InterruptedException {
        Main.play("sample_abc/sample1.abc");
        Thread.sleep(40000);
    }
    
    @Test
    public void testPlaySample2() throws InterruptedException {
        Main.play("sample_abc/sample2.abc");
        Thread.sleep(3000);
    }
    
    @Test
    public void testPlaySample3() throws InterruptedException {
        Main.play("sample_abc/sample3.abc");
        Thread.sleep(3000);
    }
    
    @Test
    public void testPlayScale() throws InterruptedException {
        Main.play("sample_abc/scale.abc");
        Thread.sleep(10000);
    }
    
    @Test
    public void testPlayWaxiesDargle() throws InterruptedException {
        Main.play("sample_abc/waxies_dargle.abc");
        Thread.sleep(22000);
    }
    
    @Test
    public void testPlayCarryOn() throws InterruptedException {
        Main.play("sample_abc/carry_on.abc");
        Thread.sleep(22000);
    }
    
    @Test
    public void testPlayPlayPhantom() throws InterruptedException {
        Main.play("sample_abc/phantom.abc");
        Thread.sleep(22000);
    }
    
    @Test
    public void testPlayBohemianRhapsody() throws InterruptedException {
        Main.play("sample_abc/bohemian_rhapsody.abc");
        Thread.sleep(220000);
    }

}
