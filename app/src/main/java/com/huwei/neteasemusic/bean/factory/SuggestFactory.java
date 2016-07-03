package com.huwei.neteasemusic.bean.factory;

import com.huwei.neteasemusic.bean.SuggestItem;
import com.huwei.neteasemusic.bean.resp.SearchSuggestResp;
import com.huwei.neteasemusic.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jerry
 * @date 2016/07/03
 */
public class SuggestFactory {

    public static final String SONGS = "songs";
    public static final String ARTISTS = "artists";
    public static final String ALBUMS = "albums";
    public static final String PLAYLISTS = "playlists";
    public static final String MVS = "mvs";

    public static final List<String> defaultOrder = new ArrayList<>();

    static {
        defaultOrder.add(SONGS);
        defaultOrder.add(ARTISTS);
        defaultOrder.add(ALBUMS);
        defaultOrder.add(PLAYLISTS);
        defaultOrder.add(MVS);
    }

//    public static SuggestItem getSuggestItem(Song song){
//        SuggestItem suggestItem = new SuggestItem();
//        suggestItem.id = song.id;
//        suggestItem.name = song.name;
//        suggestItem.searchTpye = SType.SONG;
//        return suggestItem;
//    }
//
//    public static SuggestItem getSuggestItem(Artist artist){
//        SuggestItem suggestItem = new SuggestItem();
//        suggestItem.id = artist.id;
//        suggestItem.name = artist.name;
//        suggestItem.searchTpye = SType.ARTIST;
//        return suggestItem;
//    }
//
//    public static SuggestItem getSuggestItem(Album album){
//        SuggestItem suggestItem = new SuggestItem();
//        suggestItem.id = album.id;
//        suggestItem.name = album.name;
//        suggestItem.searchTpye = SType.ALBUM;
//        return suggestItem;
//    }
//
//    public static SuggestItem getSuggestItem(Playlist playlist){
//        SuggestItem suggestItem = new SuggestItem();
//        suggestItem.id = playlist.id;
//        suggestItem.name = playlist.name;
//        suggestItem.searchTpye = SType.PLAYLIST;
//        return suggestItem;
//    }
//
//    public static SuggestItem getSuggestItem(Mv mv){
//        SuggestItem suggestItem = new SuggestItem();
//        suggestItem.id = mv.id;
//        suggestItem.name = mv.name;
//        suggestItem.searchTpye = SType.MV;
//        return suggestItem;
//    }

    public static List<SuggestItem> getSuggestList(SearchSuggestResp suggestResp) {
        List<SuggestItem> list = new ArrayList<>();

        List<String> order = null;
        if (suggestResp.order == null || suggestResp.order.size() == 0) {
            order = defaultOrder;
        } else {
            order = suggestResp.order;
        }

        for (String item : order) {
            if (StringUtils.equals(SONGS, item)) {
                fillList(list, suggestResp.songs);
            } else if (StringUtils.equals(ARTISTS, item)) {
                fillList(list, suggestResp.artists);
            } else if (StringUtils.equals(ALBUMS, item)) {
                fillList(list, suggestResp.albums);
            } else if (StringUtils.equals(PLAYLISTS, item)) {
                fillList(list, suggestResp.playlists);
            } else if (StringUtils.equals(MVS, item)) {
                fillList(list, suggestResp.mvs);
            }
        }

        return list;
    }

    private static void fillList(List<SuggestItem> list, List origin) {
        if (origin == null) {
            return;
        }

        for (int i = 0; i < origin.size(); i++) {
            list.add((SuggestItem) origin.get(i));
        }
    }
}
