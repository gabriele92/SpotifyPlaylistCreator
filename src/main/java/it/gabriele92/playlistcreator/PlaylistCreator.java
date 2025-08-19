package it.gabriele92.playlistcreator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.Playlist;
import com.wrapper.spotify.model_objects.specification.Track;
import com.wrapper.spotify.requests.data.playlists.AddItemsToPlaylistRequest;
import com.wrapper.spotify.requests.data.playlists.CreatePlaylistRequest;
import com.wrapper.spotify.requests.data.search.simplified.SearchTracksRequest;

public class PlaylistCreator {

    public static void main(String[] args) {

        try {
            SpotifyApi spotifyApi = new SpotifyApi.Builder()
                    .setAccessToken("BQAWp6ppOq1ZntlRsX0MJO6w7RysG0vP_VxF1D3UgN4ZAtRY6aC6BH5gwgpEzHsIcEljPC27RxlaRy_3AL_LF3o1DPMGh0wB9pJs_y7gpXmOUfRrMItZhKNGQcgRteHkzoNNzgMawHSm1ixy6wy0LoJ8CiNl2eLijduuFIjm-G9aJCog07cDXem2b03kmKOVsbR4RHgyUvRi_BuLXgg")
                    .build();

            final SearchTracksRequest b = spotifyApi.searchTracks("Have a nice day bon jovi").build();

            Paging<Track> paging = b.execute();
            Track[] list = paging.getItems();

            final CreatePlaylistRequest play = spotifyApi.createPlaylist("1181773944", "Have a nice day").build();
            Playlist p = play.execute();

            List<String> uris = new ArrayList<>();
            List<Track> trackList = Stream.of(list).collect(Collectors.toList());
            trackList.forEach((t) -> {
                System.out.println(t.getName() + " " + t.getArtists()[0].getName() + " " + t.getAlbum().getName());
                uris.add(t.getUri());
            });
            String[] array = new String[uris.size()];

            AddItemsToPlaylistRequest add = spotifyApi.addItemsToPlaylist(p.getId(), uris.toArray(array)).build();
            add.execute();

        } catch (Exception e) {
            System.out.println("Something went wrong!\n" + e.getMessage());
        }

    }

}
