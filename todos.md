TODO:
 * Sub-unit
 * check button-freezes
 * change POWER/SUPER-text, maybe use a pre-written text?
 * FOG
 * implement Intel-page (Status, Unit, Rules)
 * implement Save-function
 * implement Option-page
 * fix cursor on CO-unit-view
 * implement so user can flip between CO-unit-views
 * implement Unit-info-view (Weapon with effectiveness both Main and Secondary, Move, Vision, Fuel, General info)
 * implement Terrain-info-view (Defence, Funds and Repair if feasible, Movement-icons, General info)
 * implement Mini-map view (Select-view)
 * implement holding B while on terrain makes units more transparent
 * implement "Tab" to next active unit
 * only one action/unit per turn (fix later)
 * possibly move damage-box to info-row?
 * substitute ArrayList with HashMap for better performance?

 * implement ContainingUnit-interface
   - TCopter
   - Lander
 * UnitContainer
   - fix the class
   - fix for Lander-unit
 * Testing
   - CashTester
     -> fix after refactoring
   - MovementTester
   - ValidAttackTester
     -> add for Sub-unit
     -> add code for testing ammo
 * RouteArrowPath
   - rewrite algorithm to recursive
 * RouteChecker
   - add movement for team-play
 * DimensionValues
   - possibly make it into a builder-class?
 * Menu
   - take DimensionValues as parameter instead
 * MenuState
   - consider if menuIndex should be "overflowing"
 * BuildingMenu
   - add money-constraint to hinder buying unaffordable units
 * UnitMenu
   - write cargo as actual unit and not "Unit " + [number]
 * UnitMenuHandler
   - refactoring (general + covering of TCopter, APC, Lander and Cruiser)
 * TerrainType
   - add Silo
   - add Pipe
   - add Pipe-seam
   - add Wasteland?
 * Factory
   - possibly add feature to remove units to be built
   - define which units can be built in factory?
 * Airport
   - possibly add feature to remove units to be built
   - define which units can be built in airport?
 * Port
   - possibly add feature to remove units to be built
   - define which units can be built in port?
 * MiniCannon
   - refactor for readability
 * KeyListenerInputHandler
   - refactor for readability
 * UnitInfoBox
   - replace code with an image
 * Hero
   - add portrait-pictures
 * HeroPortraitPainter
   - add portrait frame-pictures
 * CommanderView
   - implement lowered power for units in view
 * UnitInfoViews
   - implement
 * ViewPainter
   - add terrainInfoView
 * DirectionalStructureImage
   - make the class an abstract class?
 * GameProperties
   - refactor
 * Cursor
   - replace code with image
 * FiringCursor
   - replace code with image
 * AttackHandler
   - refactor
 * DamageMatrixFactory
   - read damage-values from file
   - add Sub-damage
 * WeaponIndexChooser
   - add Sub-options
 
BUG-list
  - Infantry can Fire while entering APC

 
 
   
 