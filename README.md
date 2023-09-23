# SpaceInvader
***How to Run***
Open the terminal tab in your IDEA (Alt + F12 in IntelliJ) then type command 'gradle clean build run' to run the application
***Design Pattern and Where They Are***
1. Builder:
   ObjectBuilder <<interface>>
   BunkerBuilder <<class>>
   BunkerBuilderDirector <<class>>
   EnemyBuilder <<class>>
   EnemyBuilderDirector <<class>>
2. Factory:
   Projectile <<interface>>
   ProjectileFactory <<inteface>>
   PlayerProjectileFactory <<class>>
   EnemyProjectileFactory <<class>>
   EnemyProjectile <<class>>
   PlayerProjectile <<class>>
   Enemy <<class>>
   Player <<class>>
3. Strategy:
   EnemyProjectileStrategy <<interface>>
   EnemyProjectileChange <<class>>
   EnemyProjectile <<class>>
4. State:
   BunkerState <<interface>>
   BunkerGreen <<class>>
   BunkerYellow <<class>>
   BunkerRed <<class>>
   Bunker <<class>>
   GameEngine <<class>>
