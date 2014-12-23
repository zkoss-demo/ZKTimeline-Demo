package org.zkoss.ztimelinedemo;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.zkoss.timeline.Timeline;
import org.zkoss.timeline.api.TimelineRenderer;
import org.zkoss.timeline.event.TimelineEvent;
import org.zkoss.timeline.impl.SimpleTimelineEvent;
import org.zkoss.timeline.impl.SimpleTimelineModel;
import org.zkoss.timeline.impl.Util;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Label;
import org.zkoss.zul.Popup;

public class SolarSystemExplorationTimelineComposer extends GenericForwardComposer{

	private static final long serialVersionUID = 201412181000L;
	
	private Popup pop;
	private Label viewportLeft;
	private Label viewportRight;
	private Label eventDetail;
	
	//data
	Map<String, Country> countryMap;
	
	private Timeline timeline;
	private SimpleTimelineModel model;
	private TimelineRenderer<?> render;

	private SimpleDateFormat _sdf = new SimpleDateFormat("dd/MM/yyyy");
	private SimpleDateFormat _sdf1 = new SimpleDateFormat("yyyy/MM/dd");
	
	// @Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		initTimelineModel();
	}

	private void initTimelineModel() {
		countryMap = new HashMap<String, Country>();
		String[][] countryData = 
				{{"Soviet Union","pics/Flag_of_the_Soviet_Union.png"},
				{"United States","pics/Flag_of_the_United_States.png"},
				{"Germany","pics/Flag_of_Germany.png"},
				{"European Union","pics/Flag_of_Europe.png"},
				{"Japan","pics/Flag_of_Japan.png"},
				{"Russia","pics/Flag_of_Russia.png"},
				{"Italy","pics/Flag_of_Italy.png"},
				{"China","pics/Flag_of_China.png"},
				{"Canada","pics/Flag_of_Canada.png"},
				{"India","pics/Flag_of_India.png"},
				{"France","pics/Flag_of_France.png"}};
		
		for (String[] country : countryData) {
			countryMap.put(country[0], new Country(country[0], country[1]));
		}
		
		String[][] evtData = {{"Soviet Union","Sputnik 1 ","4/10/1957","First Earth orbiter "},
				{"Soviet Union","Sputnik 2 ","3/11/1957","Earth orbiter, first animal in orbit, a dog named Laika "},
				{"United States","Explorer 1 ","1/2/1958","Earth orbiter; first American orbiter, discovered Van Allen radiation belts "},
				{"United States","Vanguard 1 ","17/3/1958","Earth orbiter; oldest spacecraft still in Earth orbit "},
				{"Soviet Union","Luna 1 ","2/1/1959","First lunar flyby (attempted lunar impact?)"},
				{"United States","Pioneer 4 ","3/3/1959","Lunar flyby"},
				{"Soviet Union","Luna 2 ","12/9/1959","First lunar impact"},
				{"Soviet Union","Luna 3 ","4/10/1959","Lunar flyby; First images of far side of Moon "},
				{"United States","Pioneer 5 ","11/3/1960","Interplanetary space investigations "},
				{"Soviet Union","Sputnik 7 ","4/2/1961","Attempted Venus impact (failed to escape Earth orbit)"},
				{"Soviet Union","Venera 1 ","12/2/1961","Venus flyby (contact lost before flyby)"},
				{"Soviet Union","Vostok 1 ","12/4/1961","First manned Earth orbiter"},
				{"United States","Mercury-Redstone 3 ","5/5/1961","First American in space"},
				{"United States","Ranger 1 ","23/8/1961","Attempted lunar test flight"},
				{"United States","Ranger 2 ","18/11/1961","Attempted lunar test flight"},
				{"United States","Ranger 3 ","26/1/1962","Attempted lunar impact (missed Moon) "},
				{"United States","Mercury-Atlas 6 ","20/2/1962","First American manned Earth orbiter "},
				{"United States","Ranger 4 ","23/4/1962","Lunar impact (but unintentionally hit lunar farside and returned no data) "},
				{"Soviet Union","Sputnik 19 ","25/8/1962","Attempted Venus lander (failed to escape Earth orbit)"},
				{"United States","Mariner 2 ","27/8/1962","First successful planetary encounter, First successful Venus flyby"},
				{"Soviet Union","Sputnik 20 ","1/9/1962","Attempted Venus lander (failed to escape Earth orbit)"},
				{"Soviet Union","Sputnik 21 ","12/9/1962","Attempted Venus flyby (exploded)"},
				{"United States","Ranger 5 ","18/10/1962","Attempted lunar impact (missed Moon) "},
				{"Soviet Union","Sputnik 22 ","24/10/1962","Attempted Mars flyby (exploded)"},
				{"Soviet Union","Mars 1 ","1/11/1962","Mars flyby (contact lost)"},
				{"Soviet Union","Sputnik 24 ","4/11/1962","Attempted Mars lander (broke up) "},
				{"Soviet Union","Sputnik 25 ","4/1/1963","Attempted lunar lander (failed to escape Earth orbit)"},
				{"Soviet Union","Luna 4 ","2/4/1963","Attempted lunar lander (missed Moon) "},
				{"Soviet Union","Cosmos 21 ","11/11/1963","Attempted Venera test flight?"},
				{"United States","Ranger 6 ","30/1/1964","Lunar impact (cameras failed)"},
				{"Soviet Union","Cosmos 27 ","27/3/1964","Attempted Venus flyby (failed to escape Earth orbit)"},
				{"Soviet Union","Zond 1 ","2/4/1964","Venus flyby (contact lost)"},
				{"United States","Ranger 7 ","28/7/1964","Lunar impact"},
				{"United States","Mariner 3 ","5/11/1964","Attempted Mars flyby (failed to attain correct trajectory)"},
				{"United States","Mariner 4 ","28/11/1964","First Mars flyby "},
				{"Soviet Union","Zond 2 ","30/11/1964","Mars flyby (contact lost)"},
				{"United States","Ranger 8 ","17/2/1965","Lunar impact"},
				{"Soviet Union","Cosmos 60 ","12/3/1965","Attempted lunar lander (failed to escape Earth orbit)"},
				{"United States","Ranger 9 ","21/3/1965","Lunar impact"},
				{"Soviet Union","Luna 5 ","9/5/1965","Lunar impact (attempted soft landing) "},
				{"Soviet Union","Luna 6 ","8/6/1965","Attempted lunar lander (missed Moon) "},
				{"Soviet Union","Zond 3 ","18/7/1965","Lunar flyby"},
				{"Soviet Union","Luna 7 ","4/10/1965","Lunar impact (attempted soft landing) "},
				{"Soviet Union","Venera 2 ","12/11/1965","Venus flyby (contact lost)"},
				{"Soviet Union","Venera 3 ","16/11/1965","Venus lander (contact lost) – First spacecraft to reach another planet's surface, First Venus impact "},
				{"Soviet Union","Cosmos 96 ","23/11/1965","Attempted Venus lander (stayed in Earth orbit due to launch failure) "},
				{"Soviet Union","Luna 8 ","3/12/1965","Lunar impact (attempted soft landing?) "},
				{"United States","Pioneer 6 ","16/12/1965","Space weather observations"},
				{"Soviet Union","Luna 9 ","31/1/1966","First lunar lander "},
				{"Soviet Union","Cosmos 111 ","1/3/1966","Attempted lunar orbiter? (failed to escape Earth orbit)"},
				{"Soviet Union","Luna 10 ","31/3/1966","First lunar orbiter "},
				{"United States","Surveyor 1 ","30/5/1966","Lunar lander"},
				{"United States","Explorer 33 ","1/7/1966","Attempted lunar orbiter (failed to attain lunar orbit)"},
				{"United States","Lunar Orbiter 1 ","10/8/1966","Lunar orbiter"},
				{"United States","Pioneer 7 ","17/8/1966","Space weather observations"},
				{"Soviet Union","Luna 11 ","24/8/1966","Lunar orbiter"},
				{"United States","Surveyor 2 ","20/9/1966","Attempted lunar lander (crashed into Moon)"},
				{"Soviet Union","Luna 12 ","22/10/1966","Lunar orbiter"},
				{"United States","Lunar Orbiter 2 ","6/11/1966","Lunar orbiter"},
				{"Soviet Union","Luna 13 ","21/12/1966","Lunar lander"},
				{"United States","Lunar Orbiter 3 ","4/2/1967","Lunar orbiter"},
				{"United States","Surveyor 3 ","17/4/1967","Lunar lander"},
				{"United States","Lunar Orbiter 4 ","8/5/1967","Lunar orbiter"},
				{"Soviet Union","Venera 4 ","12/6/1967","First Venus atmospheric probe"},
				{"United States","Mariner 5 ","14/6/1967","Venus flyby"},
				{"Soviet Union","Cosmos 167 ","17/6/1967","Attempted Venus probe (failed to escape Earth orbit)"},
				{"United States","Surveyor 4 ","14/7/1967","Attempted lunar lander (crashed into Moon)"},
				{"United States","Explorer 35(IMP-E) ","19/7/1967","Lunar orbiter"},
				{"United States","Lunar Orbiter 5 ","1/8/1967","Lunar orbiter"},
				{"United States","Surveyor 5 ","8/9/1967","Lunar lander"},
				{"United States","Surveyor 6 ","7/11/1967","Lunar lander"},
				{"United States","Pioneer 8 ","13/12/1967","Space weather observations"},
				{"United States","Surveyor 7 ","7/1/1968","Lunar lander"},
				{"Soviet Union","Zond 4 ","2/3/1968","Lunar programme test flight"},
				{"Soviet Union","Luna 14 ","7/4/1968","Lunar orbiter"},
				{"Soviet Union","Zond 5 ","15/9/1968","First lunar flyby and return to Earth "},
				{"United States","Pioneer 9 ","8/11/1968","Space weather observations"},
				{"Soviet Union","Zond 6 ","10/11/1968","Lunar flyby and return to Earth"},
				{"United States","Apollo 8 ","21/12/1968","First manned lunar orbiter"},
				{"Soviet Union","Venera 5 ","5/1/1969","Venus atmospheric probe "},
				{"Soviet Union","Venera 6 ","10/1/1969","Venus atmospheric probe "},
				{"United States","Mariner 6 ","25/2/1969","Mars flyby"},
				{"United States","Apollo 9 ","3/3/1969","Manned lunar lander (LEM) flight test"},
				{"United States","Mariner 7 ","27/3/1969","Mars flyby"},
				{"United States","Apollo 10 ","18/5/1969","Manned lunar orbiter "},
				{"Soviet Union","Luna E-8-5 No.402 ","14/6/1969","Attempted lunar sample return, first attempted sample return mission "},
				{"Soviet Union","Luna 15 ","13/7/1969","Second attempted lunar sample return "},
				{"United States","Apollo 11 ","16/7/1969","First manned lunar landing and first successful sample return mission"},
				{"Soviet Union","Zond 7 ","7/8/1969","Lunar flyby and return to Earth"},
				{"Soviet Union","Cosmos 300 ","23/9/1969","Attempted lunar sample return? (failed to escape Earth orbit) "},
				{"Soviet Union","Cosmos 305 ","22/10/1969","Attempted lunar sample return? (failed to escape Earth orbit) "},
				{"United States","Apollo 12 ","14/11/1969","Manned lunar landing "},
				{"United States","Apollo 13 ","11/4/1970","Manned lunar flyby and return to Earth (manned lunar landing aborted) "},
				{"Soviet Union","Venera 7 ","17/8/1970","First Venus lander "},
				{"Soviet Union","Cosmos 359 ","22/8/1970","Attempted Venus probe (failed to escape Earth orbit)"},
				{"Soviet Union","Luna 16 ","12/9/1970","First robotic lunar sample return "},
				{"Soviet Union","Zond 8 ","20/10/1970","Lunar flyby and return to Earth"},
				{"Soviet Union","Luna 17/Lunokhod 1 ","10/11/1970","First lunar rover "},
				{"United States","Apollo 14 ","31/1/1971","Manned lunar landing "},
				{"Soviet Union","Salyut 1 ","19/4/1971","First space station "},
				{"Soviet Union","Cosmos 419 ","10/5/1971","Attempted Mars orbiter (failed to escape Earth orbit)"},
				{"United States","Mariner 9 ","30/5/1971","First Mars orbiter "},
				{"Soviet Union","Mars 2 ","19/5/1971","Mars orbiter and attempted lander; First Mars impact"},
				{"Soviet Union","Mars 3 ","28/5/1971","Mars orbiter, First Mars lander (lost contact after 14.5s) and First Mars atmospheric probe"},
				{"United States","Apollo 15 ","26/7/1971","Manned lunar landing; First manned lunar rover "},
				{"Soviet Union","Luna 18 ","2/9/1971","Attempted lunar sample return (crashed into Moon) "},
				{"Soviet Union","Luna 19 ","28/9/1971","Lunar orbiter"},
				{"Soviet Union","Luna 20 ","14/2/1972","Lunar robotic sample return"},
				{"United States","Pioneer 10 ","3/3/1972","First Jupiter flyby "},
				{"Soviet Union","Venera 8 ","27/3/1972","Venus lander"},
				{"Soviet Union","Cosmos 482 ","31/3/1972","Attempted Venus probe (failed to escape Earth orbit)"},
				{"United States","Apollo 16 ","16/4/1972","Manned lunar landing "},
				{"United States","Apollo 17 ","7/12/1972","Last manned lunar landing"},
				{"Soviet Union","Luna 21/Lunokhod 2 ","8/1/1973","Lunar rover"},
				{"United States","Pioneer 11 ","5/4/1973","Jupiter flyby and First Saturn flyby"},
				{"United States","Skylab","14/5/1973","First American space station"},
				{"United States","Explorer 49(RAE-B) ","10/6/1973","Lunar orbiter/radio astronomy "},
				{"Soviet Union","Mars 4 ","21/7/1973","Mars flyby (attempted Mars orbiter) "},
				{"Soviet Union","Mars 5 ","25/7/1973","Mars orbiter"},
				{"Soviet Union","Mars 6 ","5/8/1973","Mars orbiter and attempted lander (failed due to damage on Mars landing)"},
				{"Soviet Union","Mars 7 ","9/8/1973","Mars flyby and attempted lander (missed Mars) "},
				{"United States","Mariner 10 ","4/11/1973","Venus flyby and First Mercury flyby"},
				{"Soviet Union","Luna 22 ","2/6/1974","Lunar orbiter"},
				{"Soviet Union","Luna 23 ","28/10/1974","Attempted lunar sample return (failed due to damage on lunar landing) "},
				{"United States,Germany"," Helios-A ","10/12/1974","Solar observations"},
				{"Soviet Union","Venera 9 ","8/6/1975","First Venus orbiter and lander; First images from surface of Venus "},
				{"Soviet Union","Venera 10 ","14/6/1975","Venus orbiter and lander"},
				{"United States","Viking 1 ","20/8/1975","Mars orbiter and lander; First lander returning data and First pictures from Martian surface"},
				{"United States","Viking 2 ","9/9/1975","Mars orbiter and lander"},
				{"United States,Germany"," Helios-B ","15/1/1976","Solar observations, Closest solar approach (0.29 AU) "},
				{"Soviet Union","Luna 24 ","9/8/1976","Lunar robotic sample return"},
				{"United States","Voyager 2 ","20/8/1977","Jupiter/Saturn/first Uranus/first Neptune flyby"},
				{"United States","Voyager 1 ","5/9/1977","Jupiter/Saturn flyby, Farthest human-made object – currently -2014 about 130 AU "},
				{"United States","Pioneer Venus 1 ","20/5/1978","Venus orbiter"},
				{"United States","Pioneer Venus 2 ","8/8/1978","Venus atmospheric probes "},
				{"United States,European Union","ISEE-3","12/8/1978","Solar wind investigations; later redesignated International Cometary Explorer and performed Comet Giacobini-Zinner and Comet Halley flybys – First comet flyby"},
				{"Soviet Union","Venera 11 ","9/9/1978","Venus flyby and lander"},
				{"Soviet Union","Venera 12 ","14/9/1978","Venus flyby and lander"},
				{"Soviet Union","Venera 13 ","30/10/1981","Venus flyby and lander"},
				{"Soviet Union","Venera 14 ","4/11/1981","Venus flyby and lander"},
				{"Soviet Union","Venera 15 ","2/6/1983","Venus orbiter"},
				{"Soviet Union","Venera 16 ","7/6/1983","Venus orbiter"},
				{"Soviet Union","Vega 1 ","15/12/1984","Venus flyby, lander and first balloon; continued on to Comet Halley flyby"},
				{"Soviet Union","Vega 2 ","21/12/1984","Venus flyby, lander and balloon; continued on to Comet Halley flyby "},
				{"Japan","Sakigake","7/1/1985","Comet Halley flyby "},
				{"European Union","Giotto","2/7/1985","Comet Halley flyby "},
				{"Japan","Suisei (Planet-A)","18/8/1985","Comet Halley flyby "},
				{"Soviet Union","Mir","20/2/1986","First modular space station (completion 1996)"},
				{"Soviet Union","Phobos 1 ","7/7/1988","Attempted Mars orbiter/Phobos landers (contact lost)"},
				{"Soviet Union","Phobos 2 ","12/7/1988","Mars orbiter/attempted Phobos landers (contact lost)"},
				{"United States","Magellan","4/5/1989","Venus orbiter"},
				{"United States","Galileo","18/10/1989","Venus flyby, first Asteroid flyby, first Asteroid moon discovery, first Jupiter orbiter/atmospheric probe "},
				{"Japan","Hiten (Muses-A) ","24/1/1990","Lunar flyby and orbiter"},
				{"United States, European Union","Ulysses ","6/10/1990","Solar polar orbiter "},
				{"Japan, United States, United Kingdom ","Yohkoh (Solar-A) ","30/8/1991","Solar observations"},
				{"United States","Mars Observer ","25/9/1992","Attempted Mars orbiter (contact lost) "},
				{"United States","Clementine","25/1/1994","Lunar orbiter/attempted asteroid flyby"},
				{"United States","WIND","1/11/1994","Solar wind observations "},
				{"European Union, United States","SOHO ","2/12/1995","Solar observatory"},
				{"United States","NEAR Shoemaker ","17/2/1996","Eros orbiter, first near-Earth asteroid flyby, first asteroid orbit and first asteroid landing "},
				{"United States","Mars Global Surveyor ","7/11/1996","Mars orbiter"},
				{"Russia","Mars 96","16/11/1996","Attempted Mars orbiter/landers (failed to escape Earth orbit)"},
				{"United States","Mars Pathfinder ","4/12/1996","Mars lander and first planetary rover"},
				{"United States","ACE","25/8/1997","Solar wind and \"space weather\" observations "},
				{"United States, European Union, Italy","Cassini–Huygens Union ","15/10/1997","First Saturn orbiter and first outer planet lander"},
				{"China","AsiaSat 3/HGS-1 ","24/12/1997","Lunar flyby"},
				{"United States","Lunar Prospector ","7/1/1998","Lunar orbiter"},
				{"Japan","Nozomi (probe) (also known as Planet-B)","3/7/1998","Attempted Mars orbiter (failed to enter Mars orbit)"},
				{"United States","Deep Space 1 (DS1)","24/10/1998","Asteroid and comet flyby"},
				{"United States, Russia, European Union, Japan, Canada","","20/11/1998","International Space Station (planned completion 2013)"},
				{"United States","Mars Climate Orbiter ","11/12/1998","Attempted Mars orbiter (orbit insertion failed)"},
				{"United States","Mars Polar Lander/Deep Space 2","3/1/1999","Attempted Mars lander/penetrators (contact lost) "},
				{"United States","Stardust","7/2/1999","First comet coma sample return – returned 15/1/2006"},
				{"United States","2001 Mars Odyssey ","7/4/2001","Mars orbiter"},
				{"United States","Genesis","8/8/2001","First solar wind sample return "},
				{"United States","CONTOUR","3/7/2002","Attempted flyby of three comet nuclei (lost in space) "},
				{"Japan","Hayabusa (Muses-C) ","9/5/2003","Asteroid lander and First sample return from asteroid"},
				{"United States","Mars Exploration Rovers ","10/6/2003 /7/7/2003","Two Mars rovers (\"Spirit\" and Opportunity)"},
				{"European Union,United Kingdom","Mars Express/Beagle 2 ","1/6/2003","Mars orbiter/lander (lander failure)"},
				{"European Union","SMART-1","27/9/2003","Lunar orbiter"},
				{"China","Shenzhou 5 ","15/10/2003","China's first manned Earth orbiter "},
				{"European Union","Rosetta and Philae ","2/3/2004","First comet orbiter and lander (Entered orbit and landed in 2014) "},
				{"United States","MESSENGER","3/8/2004","First Mercury orbiter (Achieved orbit 18/3/2011)"},
				{"United States","Deep Impact ","12/1/2005","First comet impact "},
				{"United States","Mars Reconnaissance Orbiter ","12/8/2005","Mars orbiter"},
				{"European Union","Venus Express ","9/11/2005","Venus polar orbiter "},
				{"United States","New Horizons ","19/1/2006","First Pluto/Charon and Kuiper Belt flyby (Expected arrival 14/7/2015) "},
				{"Japan,United States,United Kingdom ","Hinode (Solar-B) ","22/9/2006","Solar orbiter"},
				{"United States","STEREO","26/10/2006","Two spacecraft, solar orbiters"},
				{"United States","Phoenix","4/8/2007","Mars polar lander "},
				{"Japan","SELENE (Kaguya) ","14/9/2007","Lunar orbiters"},
				{"United States","Dawn","27/9/2007","Asteroid Ceres and Vesta orbiter (Entered orbit around Vesta on 16/7/2011) "},
				{"China","Chang'e 1 ","24/10/2007","Lunar orbiter"},
				{"India","Chandrayaan-1","22/10/2008","Lunar orbiter and impactor – Discovered water on the moon"},
				{"United States","Lunar Reconnaissance Orbiter/LCROSS ","18/6/2009","Lunar polar orbiter and lunar impactor"},
				{"United States","Solar Dynamics Observatory ","11/2/2010","Continuous solar monitoring "},
				{"Japan","Akatsuki (Planet-C) ","20/5/2010","Venus orbiter (orbit insertion failed in 2010 / postponed to 2016–17) "},
				{"France","PICARD","15/6/2010","Solar monitoring"},
				{"China","Chang'e 2 ","1/10/2010","Lunar orbiter, Asteroid 4179 Toutatis flyby"},
				{"United States","Juno","5/8/2011","Jupiter orbiter"},
				{"United States","GRAIL","10/9/2011","Two spacecraft, Lunar orbiters"},
				{"China","Tiangong (Project 921-2) ","29/9/2011","First Chinese space station[1] (planned completion around 2020)"},
				{"Russia,China","Fobos-Grunt and Yinghuo-1 ","8/11/2011","Phobos orbiter, lander and sample return (Russia), Mars orbiter (China) – failed to escape Earth orbit"},
				{"United States","Mars Science Laboratory (Curiosity Rover) ","26/11/2011","large Mars 900 kg Rover (landed 6/8/2012) "},
				{"United States","Van Allen Probes (RBSP) ","30/8/2012","Earth Van Allen radiation belts study"},
				{"United States","IRIS","27/6/2013","Solar observations"},
				{"United States","LADEE","6/9/2013","Lunar orbiter"},
				{"Japan","Hisaki","14/9/2013","Planetary atmosphere observatory "},
				{"India","Mars Orbiter Mission (Mangalyaan) ","5/11/2013","Mars orbiter"},
				{"United States","MAVEN","18/11/2013","Mars orbiter"},
				{"China","Chang'e 3 ","1/12/2013","First Chinese lunar lander and rover (most recent lander since Russian Luna in 1976)"},
				{"China","Chang'e 5-T1 / 4M mission ","23/10/2014","Lunar flyby and Earth reentry probe; technology demonstration to prepare for Chang'e 5 mission"},
				{"Japan","Hayabusa 2 ","3/12/2014","Asteroid lander and sample return"}};
		
		model = new SimpleTimelineModel();
		for (int i = 0; i < evtData.length; i++) {
			String[] counties = evtData[i][0].split(",");
			List<Country> countryList = new ArrayList<Country>();
			for (String c : counties) {
				c = c.trim();
				if (countryMap.containsKey(c)) {
					countryList.add(countryMap.get(c));
				}
			}
			SolarSystemExplorationEvent ee;
			try {
				ee = new SolarSystemExplorationEvent(_sdf.parse(evtData[i][2]), countryList,  evtData[i][1],  evtData[i][3], "");
				model.add(ee);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		render = new SolarSystemExplorationTimelineRenderer();
		timeline.setModel(model);
		timeline.setRenderer(render);
		timeline.setEventTagWidth(180);
		timeline.setEventTagHeight(50);
		try {
			timeline.setMinTimeBound(_sdf.parse("1/1/1950").getTime());
			timeline.setMaxTimeBound(_sdf.parse("1/1/2025").getTime());
			Date left = _sdf.parse("1/10/1995");
			Date right = _sdf.parse("1/7/1998");
			timeline.setViewportLeftTime(left.getTime());
			timeline.setViewportRightTime(right.getTime());
//			timeline.setSubViewportLeftTime(_sdf.parse("1/1/1995").getTime());
//			timeline.setSubViewportRightTime(_sdf.parse("1/12/1998").getTime());
			
			timeline.setMinViewPeriodLevel(Util.DAY_LEVEL);
			timeline.setMaxViewPeriodLevel(Util.YEAR_LEVEL);
			viewportLeft.setValue(_sdf1.format(left));
			viewportRight.setValue(_sdf1.format(right));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public void onEventTooltip$timeline(TimelineEvent evt) {
		String data = ((SolarSystemExplorationEvent)evt.getEvent()).getDescription();
		((Label)pop.getFirstChild()).setValue(data);
	}

	public void onViewportChange$timeline(ForwardEvent evt) {
		String[] data = (String[]) evt.getOrigin().getData();
		Date left = new Date();
		left.setTime(new BigDecimal(data[0]).longValue());
		Date right = new Date();
		right.setTime(new BigDecimal(data[1]).longValue());
		viewportLeft.setValue(_sdf1.format(left));
		viewportRight.setValue(_sdf1.format(right));
	}
	
	public void onEventSelect$timeline(ForwardEvent evt) {
		List<SolarSystemExplorationEvent> events = (List<SolarSystemExplorationEvent>)evt.getOrigin().getData();
		if (events.size() > 0) {
			SolarSystemExplorationEvent event = events.get(0);
			String data = "@" + _sdf1.format(event.getStartDate()) + ": " + event.getTopic() + " - " + event.getDescription();
			eventDetail.setValue(data);
		}
	}
	
	
}