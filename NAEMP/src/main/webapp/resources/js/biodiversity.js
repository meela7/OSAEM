// Dimensions of sunburst.
var width = 880;
var height = 880;
var radius = Math.min(width, height) / 2;
var titles = ["Phylum","Class","Order","Family","Genus","Species"];

// Mapping of step names to colors.
var colors = ["#c4c3c3", "#b6c3ab", "#a7c192", "#98c079", "#89bf5c", "#78c042"];
var stat_colors = ["#000000", "#ed174a", "#d95934", "#f9a01b", "#009087", "#3cb5fc", "#8f898e", "#938b7f"];
var pop_colors = ["#00cc00", "#c0c000", "#cc0000", "#938b7f"];

var vis = d3.select("#tree").append("svg:svg")
    .attr("width", width)
    .attr("height", height)
    .append("svg:g")
    .attr("id", "container")
    .attr("transform", "translate(" + width / 2 + "," + height / 2 + ")");

var partition = d3.layout.partition()
    .size([2 * Math.PI, radius * radius])
    .value(function(d) { return 1; });

var arc = d3.svg.arc()
    .startAngle(function(d) { return d.x; })
    .endAngle(function(d) { return d.x + d.dx; })
    .innerRadius(function(d) { return Math.sqrt(0.8*d.y); })
    .outerRadius(function(d) { return Math.sqrt(d.y + d.dy); });

// Main function to draw and set up the visualization, once we have the data.
function createVisualization(json) {

  // Bounding circle underneath the sunburst, to make it easier to detect
  // when the mouse leaves the parent g.
  vis.append("svg:circle")
      .attr("r", radius/2)
      .style("opacity", 1)
      .style("fill", "#938b7f");

  var nodes = partition.nodes(json)
  var path = vis.data([json]).selectAll("path")
      .data(nodes)
      .enter().append("svg:path")
      .attr("display", function(d) { return d.depth ? null : "none"; })
      .attr("d", arc)
      .attr("fill-rule", "evenodd")
      .style("stroke", "#2f261a")
      .style("stroke-width", 0.2)
      .style("fill", function(d) { return colors[d.depth-1]; })
      .style("opacity", 1)
      .on("mouseover", mouseover)

  // Add the mouseleave handler to the bounding circle.
  d3.select("#tree").on("mouseleave", mouseleave);

  filterSetup(nodes);
};

// Interpolate the arcs in data space.
function arcTween(a) {
  var i = d3.interpolate({x: a.x0, dx: a.dx0}, a);
  return function(t) {
    var b = i(t);
    a.x0 = b.x;
    a.dx0 = b.dx;
    return arc(b);
  };
}

// Fade all but the current sequence
function mouseover(d) {
  d3.select("#taxon")
     .text(titles[d.depth-1]+":");

  d3.select("#sciname")
      .text(function() {
        if ((d.depth-1) == 5) {
          return ( d.parent.name + " " + d.name);
        } else {
          return d.name;
        }
      });
  
  d3.select("#commonname")
    .text(function() {
      if ((d.depth-1) == 5) {
          return ( "(" + d.comName+ ")" );
        } else {
          return null;
        }
      });

  d3.select("#explanation")
      .style("visibility", "");
      
  d3.select("#institutions_bars")
      .style("visibility", "");

  processBarData(d)
}

// Restore everything to full opacity when moving off the visualization.
function mouseleave(d) {
	console.log("leave");
  // Deactivate all segments during transition.
  d3.selectAll("path").on("mouseover", null);

  // Transition each segment to full opacity and then reactivate it.
  d3.selectAll("path")
      .transition()
      .duration(1)
      //.style("opacity", 0.2)
      .each("end", function() {
          d3.select(this).on("mouseover", mouseover);
      });

  d3.select("#explanation")
      .transition()
      .duration(1)
      .style("visibility", "hidden");

  d3.select("#institutions_bars")
      .style("visibility", "hidden");
      
  d3.select("#institutions_numbers").selectAll("p")
      .data([]).exit().remove();
  
  d3.select("#institutions_label").selectAll("p")
      .data([]).exit().remove();
}

// Given a node in a partition layout, return an array of all of its ancestor
// nodes, highest first, but excluding the root.
function getAncestors(node) {
  var path = [];
  path.push(node);
  for (var i=0; i<5; i++) {
    path.forEach(function(candidate) {
      if (path.indexOf(candidate.parent) < 0) {
        path.push(candidate.parent);
      }
    });
  }
  return path;
}

function getDescendants(node) {
  var path = [];
  var current = node;
  var maxDepth = 6;
  var diff = maxDepth - node.depth;
  path.push(current);

  for (var i=0;i<diff;i++){
    path.forEach(function(child){
      child.children.forEach(function(sub){
        if(path.indexOf(sub) < 0){
          path.push(sub);
        }
      });
    });
  }
  return path;
}
  
function buildHierarchy(csv) {
  var root = {"name": "root", "children": []};
  for (var i = 1; i < csv.length; i++) { //Start at 1 because of header
    var _comName = csv[i][6];
    var _tor = +csv[i][9];
    var _bro = +csv[i][8];
    var _san = +csv[i][10];
    var _hen = +csv[i][11];
    var _status = csv[i][7];
    var _popTrend = csv[i][13];
    var _linkNum = csv[i][12];
    
    var currentNode = root;
    for (var j = 0; j < 6; j++) {
      var children = currentNode["children"];
      var nodeName = csv[i][j];
      var childNode;
      if (j + 1 < 6) { // Not at the end of the sequence; move down the tree.
 	var foundChild = false;
 	for (var k = 0; k < children.length; k++) {
 	  if (children[k]["name"] == nodeName) {
 	    childNode = children[k];
 	    foundChild = true;
 	    break;
 	  }
 	}
 	if (!foundChild) {      // If we dont already have a child node for this branch, create it.
 	  childNode = {"name": nodeName, "children": []};
 	  children.push(childNode);
 	}
 	currentNode = childNode;
      } else {
 	// Reached the end of the sequence; create a leaf node.
 	childNode = {"name": nodeName, 
 	             "comName": _comName,
 	             "bro": _bro,
 	             "tor": _tor,
 	             "san": _san,
 	             "hen": _hen,
 	             "status": _status,
 	             "popTrend": _popTrend,
 	             "linkNum": _linkNum};
 	children.push(childNode);
      }
    }
  }
  return root;
};

// FILTER SETUP
function filterSetup(node) {
  
  //Initial state
  d3.select("#totalNumber")
     .html(function() {
         var allSpecies = d3.selectAll(node).filter(function() { return this.depth==6; })[0];
         return allSpecies.length;
    });
    d3.select("#speciesText")
     .html("Species");
  
  d3.selectAll("input").on("change", function change() {
    var allCheck = document.getElementById("all").checked;
    var torCheck = document.getElementById("tor").checked;
    var broCheck = document.getElementById("bro").checked;
    var sanCheck = document.getElementById("san").checked;
    var henCheck = document.getElementById("hen").checked;
    var statCheck = document.getElementById("stat").checked;
    var popCheck = document.getElementById("pop").checked;
    var specArray = [];
    var masterArray = [];
   
    if (allCheck) {
      var filteredData = (d3.selectAll(node).filter(function() { return (this.tor == 1 || this.tor == 0); }))[0];
      filteredData.forEach(function(single){
        var parentArray = getAncestors(single);
	parentArray.forEach(function(parent){
	  masterArray.push(parent);
	});
      });
    }
    if (torCheck) {
      var filteredData = (d3.selectAll(node).filter(function() { return this.tor == 1; }))[0];
      filteredData.forEach(function(single){
        var parentArray = getAncestors(single);
	parentArray.forEach(function(parent){
	  masterArray.push(parent);
	});
      });
    }
    if (broCheck) {
      var filteredData = (d3.selectAll(node).filter(function() { return this.bro == 1; }))[0];
      filteredData.forEach(function(single){
        var parentArray = getAncestors(single);
	parentArray.forEach(function(parent){
	  masterArray.push(parent);
	});
      });
    }
    if (sanCheck) {
      var filteredData = (d3.selectAll(node).filter(function() { return this.san == 1; }))[0];
      filteredData.forEach(function(single){
        var parentArray = getAncestors(single);
	parentArray.forEach(function(parent){
	  masterArray.push(parent);
	});
      });
    }
    if (henCheck) {
      var filteredData = (d3.selectAll(node).filter(function() { return this.hen == 1; }))[0];
      filteredData.forEach(function(single){
        var parentArray = getAncestors(single);
	parentArray.forEach(function(parent){
	  masterArray.push(parent);
	});
      });
    }
    
    d3.select("#totalNumber")
     .html(function() {
         return filteredData.length;
    });
    d3.select("#speciesText")
     .html("Species");
    
    vis.selectAll("path").style("fill", "#2f261a");

    if (statCheck) {
      vis.selectAll("path").filter(function(node) {
        return (masterArray.indexOf(node) >= 0);
      })
      .style("fill", "#938b7f");
      
      vis.selectAll("path").filter(function(node) {
        return (masterArray.indexOf(node) >= 0 && node.depth==6);
      })
      .style("fill", function(d) {
        if (d.status == "EW") {
          return stat_colors[0];
        } else if (d.status == "CR") {
          return stat_colors[1];
        } else if (d.status == "EN") {
          return stat_colors[2];
        } else if (d.status == "VU") {
          return stat_colors[3];
        } else if (d.status == "NT") {
          return stat_colors[4];
        } else if (d.status == "LC") {
          return stat_colors[5];
        } else if (d.status == "DD") {
          return stat_colors[6];
        } else {
          return stat_colors[7];
        }
      });
    } else if (popCheck) {
      vis.selectAll("path").filter(function(node) {
        return (masterArray.indexOf(node) >= 0);
      })
      .style("fill", "#938b7f");
      
      vis.selectAll("path").filter(function(node) {
        return (masterArray.indexOf(node) >= 0 && node.depth==6);
      })
      .style("fill", function(d) {
        if (d.popTrend == "in") {
          return pop_colors[0];
        } else if (d.popTrend == "st") {
          return pop_colors[1];
        } else if (d.popTrend == "de") {
          return pop_colors[2];
        } else {
          return pop_colors[3];
        }
      });    
    }
    else {
      vis.selectAll("path").filter(function(node) {
        return (masterArray.indexOf(node) >= 0);
      })
      .style("fill", function(d) { return colors[d.depth-1]; })
    } 
  });
}

// Use d3.text and d3.csv.parseRows so that we do not need to have a header
// row, and can receive the csv as an array of arrays.
//d3.text("taxom-fish.csv", function(text) {
d3.text("//www.dropbox.com/s/3mdkqx4obgcpqlf/zoodata.csv", function(text) {
  var csv = d3.csv.parseRows(text);
  var json = buildHierarchy(csv);
  createVisualization(json);
});

//-----------------------------------------------------------------------

/* BAR GRAPH */

var barGraphMargin = {top: 0, right: 0, bottom: 0, left: 0},
    barGraphWidth = 395 - barGraphMargin.left - barGraphMargin.right,
    barGraphHeight = 126 - barGraphMargin.top - barGraphMargin.bottom;

var x = d3.scale.linear().range([0, barGraphWidth]);
var y = d3.scale.ordinal().rangeRoundBands([barGraphHeight, 0], .1);

var barsvg = d3.select("#institutions_bars").append("svg")
    .attr("width", barGraphWidth + barGraphMargin.left + barGraphMargin.right)
    .attr("height", barGraphHeight + barGraphMargin.top + barGraphMargin.bottom)
  .append("g")
    .attr("transform", "translate(" + barGraphMargin.left + "," + barGraphMargin.top + ")");

function processBarData(node) {
  var newBarData = [{"letter":"A","frequency":0},
               {"letter":"B","frequency":0},
               {"letter":"C","frequency":0},
               {"letter":"D","frequency":0}];
  
  var countBro = 0, countHen = 0, countSan = 0, countTor = 0;
  
  // Note: these arrays must be in reverse alphabetical order, since the visual starts from the bottom rather than the top
  if (node.depth == 6) {
    if (node.hen == 1) {
      newBarData[2].frequency++;
    }
    if (node.san == 1) {
      newBarData[1].frequency++;
    }
    if (node.tor == 1) {
      newBarData[0].frequency++;
    }
    if (node.bro == 1) {
      newBarData[3].frequency++;
    }
    barGraph(newBarData)
  } else {
    childArray = getDescendants(node);
    for (i=0;i<childArray.length;i++) {
      if (childArray[i].tor == 1) {
        countTor++;
      }
      if (childArray[i].bro == 1) {
        countBro++;
      }
      if (childArray[i].san == 1) {
        countSan++;
      }
      if (childArray[i].hen == 1) {
        countHen++;
      }
    }
    newBarData[3].frequency = countBro;
    newBarData[2].frequency = countHen;
    newBarData[1].frequency = countSan;
    newBarData[0].frequency = countTor;
    barGraph(newBarData)
  }
}

function barGraph(barData) {
    x.domain([0, d3.max(barData, function(d) { return d.frequency; })]);
    y.domain(barData.map(function(d) { return d.letter; }));
    
    barsvg.selectAll("g").data([]).exit().remove();
    barsvg.selectAll(".bar").data([]).exit().remove();

    barsvg.selectAll(".bar")
        .data(barData)
        .enter().append("rect")
        .attr("class", "bar")
        .attr("x", 0)
        .attr("width", function(d) { return x(d.frequency); })
        .attr("y", function(d) { return y(d.letter); })
        .attr("height", 20); //function(d) { return y.rangeBand() }

    var printLabel = document.getElementById('institutions_label');
    printLabel.innerHTML = "";
    printLabel.innerHTML = printLabel.innerHTML + "<p># of species by taxon</p>";

    var printP = document.getElementById('institutions_numbers');
    printP.innerHTML = "";
    printP.innerHTML = printP.innerHTML + "<p>" + barData[3].frequency + "</p>"
      + printP.innerHTML + "<p>" + barData[2].frequency + "</p>"
      + printP.innerHTML + "<p>" + barData[1].frequency + "</p>"
      + printP.innerHTML + "<p>" + barData[0].frequency + "</p>";
}

function type(d) {
  d.frequency = +d.frequency;
  return d;
}