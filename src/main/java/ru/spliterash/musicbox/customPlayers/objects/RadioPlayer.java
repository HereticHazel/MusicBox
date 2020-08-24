package ru.spliterash.musicbox.customPlayers.objects;

import com.xxmicloxx.NoteBlockAPI.songplayer.RadioSongPlayer;
import lombok.Getter;
import org.bukkit.entity.Player;
import ru.spliterash.musicbox.customPlayers.interfaces.IPlayList;
import ru.spliterash.musicbox.customPlayers.interfaces.PlayerSongPlayer;
import ru.spliterash.musicbox.customPlayers.models.AllPlayerModel;
import ru.spliterash.musicbox.customPlayers.models.PlayerPlayerModel;
import ru.spliterash.musicbox.players.PlayerWrapper;
import ru.spliterash.musicbox.song.MusicBoxSong;
import ru.spliterash.musicbox.utils.SongUtils;

/**
 * Проигрыватель для игрока
 * Если игрок захочет послушать музыку никого не напрягая
 */
@Getter
public class RadioPlayer extends RadioSongPlayer implements PlayerSongPlayer {
    private final PlayerPlayerModel model;
    private final AllPlayerModel musicBoxModel;

    public RadioPlayer(MusicBoxSong song, IPlayList list, PlayerWrapper wrapper) {
        super(song.getSong());
        this.musicBoxModel = new AllPlayerModel(this, song, list, SongUtils.getRunNextRunnable(wrapper, list));
        this.model = new PlayerPlayerModel(wrapper, musicBoxModel);
        musicBoxModel.runPlayer();

    }


    @Override
    public void destroy() {
        super.destroy();
        model.destroy();
        musicBoxModel.destroy();
    }

    @Override
    public void playTick(Player player, int tick) {
        super.playTick(player, tick);
        if (player.equals(model.getWrapper().getPlayer())) {
            model.nextTick(getSong().getLength(), tick);
        }
    }
}