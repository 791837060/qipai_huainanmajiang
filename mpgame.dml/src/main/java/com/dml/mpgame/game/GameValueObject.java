package com.dml.mpgame.game;

import java.util.ArrayList;
import java.util.List;

import com.dml.mpgame.game.player.GamePlayerOnlineState;
import com.dml.mpgame.game.player.GamePlayerState;

public abstract class GameValueObject {

	private String id;
	private String createPlayerId;
	private GameState state;
	private List<GamePlayerValueObject> players;

	public GameValueObject(Game game) {
		id = game.getId();
		createPlayerId = game.getCreatePlayerId();
		state = game.getState();
		players = new ArrayList<>();
		game.getIdPlayerMap().values().forEach((player) -> players.add(new GamePlayerValueObject(player)));
	}

	public GamePlayerOnlineState findPlayerOnlineState(String playerId) {
		for (GamePlayerValueObject player : players) {
			if (player.getId().equals(playerId)) {
				return player.getOnlineState();
			}
		}
		return null;
	}

	public GamePlayerState findPlayerState(String playerId) {
		for (GamePlayerValueObject player : players) {
			if (player.getId().equals(playerId)) {
				return player.getState();
			}
		}
		return null;
	}

	public List<String> allPlayerIds() {
		List<String> allPlayerIds = new ArrayList<>();
		players.forEach((player) -> allPlayerIds.add(player.getId()));
		return allPlayerIds;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCreatePlayerId() {
		return createPlayerId;
	}

	public void setCreatePlayerId(String createPlayerId) {
		this.createPlayerId = createPlayerId;
	}

	public GameState getState() {
		return state;
	}

	public void setState(GameState state) {
		this.state = state;
	}

	public List<GamePlayerValueObject> getPlayers() {
		return players;
	}

	public void setPlayers(List<GamePlayerValueObject> players) {
		this.players = players;
	}

}
