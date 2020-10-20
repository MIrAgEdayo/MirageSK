現在実装済みの機能: 3つ

```
Effects:
	EffGlow:
		ID: EffGlow
		Description:
			Entities can be made to glow or unglow with a specified color only for a specific player.
			エンティティを指定した色で特定のプレイヤーにだけ発光させたり、解除したりできます。
		Examples:
			on join:
				force player glowing with color "red" for all players
				wait 3 second
				force player unglow for all players
		Since:
			1.0
		Patterns:
			[(make|force)] %entities% glow[ing] with color %string% [(for|to) %-players%]
			[(make|force)] %entities% unglow[ing] (for|to) %players%
Expressions:
	ExprGodMode:
		ID: ExprGodMode
		Description:
			Returns(set, etc...) godmode of player using WorldGuard
			WorldGuardを使用してプレイヤーをgodモードにしたり解除したりできます。
		Examples:
			on join:
				if godmode of player is false:
					set godmode of player to true
					send "Godmode enabled!"
		Since:
			1.0
		Return type: Boolean
		Changers:
			set
			delete
			reset
		Patterns:
			[the] godmode of %player%
			%player%'[s] godmode
	ExprSubAccounts:
		ID: ExprSubAccounts
		Description:
			Returns online subaccounts of players
			プレイヤーのオンラインのサブアカウントを取得します。
		Examples:
			on join:
				if size of subaccounts of player > 3:
					kick the player due to "You have too many subaccounts!"
		Since:
			1.0
		Return type: Player
		Patterns:
			[the] subaccount[s] of %players%
```