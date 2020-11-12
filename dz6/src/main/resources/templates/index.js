// Get values from price dropdowns
/*
var prefixRs = '<i class="fa fa-gbp"></i> ',
$minPrice = $('#minPrice'),
$maxPrice = $('#maxPrice'),
$rangeInput = $('.range-values'),
setRangeValues = function () {
	var minValue = $minPrice.val(),
	maxValue = $maxPrice.val(),
	filterValues = [],
	filterString = '';

	if (minValue > 1 || maxValue < 5000000) {
		for (i = minValue; i <= maxValue; i += 100000) {
			filterValues.push(".price-" + i);
		}
	}
	filterString = filterValues.join(',');
	$rangeInput.val(filterString);
};

$priceDropdowns = $('select.filterPrice');
$priceDropdowns.change(function() {
	setRangeValues();

	console.log(minValue+', '+maxValue);
	//console.log('min: '+$('#minPrice').val());
	//console.log('max: '+$('#maxPrice').val());

	mixedFilter.parseFilters();
});
*/

// Custom mixitup filter
var mixedFilter = {

  $filters: null,
  $reset: null,
  $clear: null,
  groups: [],
  outputArray: [],
  outputString: '',

  init: function() {
    var self = this;
    self.$filters = $('#js-filters');
    self.$reset = $('.js-reset');
    self.$clear = $('.js-clear');
    self.$container = $('#js-results');
    self.$minPrice = $("#minPrice");
    self.$maxPrice = $("#maxPrice");

    self.$filters.find('fieldset').each(function() {
      var $this = $(this);
      self.groups.push({
        $buttons: $this.find('.filter'),
        $dropdowns: $this.find('select.filterDropdown'),
        //$prices : $this.find('select.filterPrice'),
        $inputsText: $this.find('input[type="text"]'),
        $inputs: $(this).find('input.range-values'),
        //active : ''
        active: []
      });
    });
    //self.groups = self.$filters.find('[data-mix-filter="true"]');

    self.bindHandlers();
  },

  // The bindHandlers method will listen for whenever a button is clicked.
  bindHandlers: function() {

    var self = this;

    // Handle button clicks
    self.$filters.on('click', '.filter', function(e) {
      e.preventDefault();

      var $button = $(this);

      // If the button is active, remove the active class, else make active and deactivate others.
      $button.hasClass('active') ?
        $button.removeClass('active') :
        $button.addClass('active').siblings('.filter').removeClass('active');

      // Turn this off for now, as firing on every click
      self.parseFilters();
    });

    // Handle dropdown changes clicks
    self.$filters.on('change', 'select.filterDropdown', function(e) {
      e.preventDefault();
      self.parseFilters();
    });

    // Handle PRICE dropdown changes clicks
    self.$filters.on('change', 'select.filterPrice', function(e) {
      e.preventDefault();
      self.parseFilters();
      //self.filterPrice();
    });

    // Handle key up on inputs
    self.$filters.on('change', 'input[type="text"]', function() {

      var $input = $(this);
      //console.log($input.val());
      $input.attr('data-filter', '[class*="' + $input.val().replace(/ /, '-') + '"]');
      if ($input.val() == '')
        $input.attr('data-filter', '');
      //console.log($input.attr('data-filter'));

      // Add a little delay when user is typing...
      setTimeout(function() {
        self.parseFilters();
      }, 600);

    });

    // Handle reset click
    self.$reset.on('click', function(e) {
      e.preventDefault();
      self.$filters.find('.filter').removeClass('active');
      self.$filters.find('.show-all').addClass('active');
      self.$filters.find('select').val('');
      self.$filters.find('input[type="text"]').val('').attr('data-filter', '');
      self.parseFilters();
    });

    // Handle clear text
    self.$clear.on('click', function(e) {
      self.$filters.find('input[type="text"]').val('').attr('data-filter', '');
      self.parseFilters();
    });

  },

  parseFilters: function() {
    var self = this;

    // loop through each filter group and grap the value from each one.
    for (var i = 0, group; group = self.groups[i]; i++) {
      group.active = []; // reset arrays

      var activeButtons = group.$buttons.length ? group.$buttons.filter('.active').attr('data-filter') || '' : '';
      var activeSelect = group.$dropdowns.length ? group.$dropdowns.val() || '' : '';
      var activeText = group.$inputsText.length ? group.$inputsText.attr('data-filter') : '';

      /*group.$inputs.each(function () {
      	var $input = $(this);
      	if ($input.is('.range-values')) {
      		group.active = $input[0].value.split(',');
      	}
      });
      var activePrice = group.active.length;*/

      group.active = activeButtons + activeSelect + activeText;
      //group.active = activeSelect+activeText+activePrice;
      //group.active = activeButtons+activeSelect+activeText+activePrice;
      //console.log('group.active: ' + group.active);
    }

    self.concatenate();
  },

  // The "concatenate" method will crawl through each group, concatenating filters as desired:
  concatenate: function() {
    var self = this;

    self.outputString = ''; // Reset output string

    for (var i = 0, group; group = self.groups[i]; i++) {
      self.outputString += group.active;
    }

    // If the output string is empty, show all rather than none:
    !self.outputString.length && (self.outputString = 'all');







// helpchrisplz edits

    var extraDropDownColours = [];
    $(".filterPrice").each(function(){
      extraDropDownColours.push($("option:selected", this).val());
    });

    $.each(extraDropDownColours, function(index, item) {
    //alert(item);
      if (item != "select") {

        if(self.outputString == "all"){
          self.outputString += ',.' +item.toLowerCase();
        } else {

          // Removes all of the commas within your string
          var suckIt = self.outputString.split(',');

          // Iterate through each value
          self.outputString = "";
          for(var i = 0; i < suckIt.length; i++){
            self.outputString += suckIt[i] + '.' + item.toLowerCase() + ',';
          }

          if (self.outputString.substring(self.outputString.length-1) == ",")
          {
            self.outputString = self.outputString.substring(0, self.outputString.length-1);
          }

        }
      }

     }); //each
//  end helpchrisplz edits









    // we can check the console here to take a look at the filter string that is produced
    console.log(self.outputString);

    // now save these settings to a cookie
    //$.cookie("mixitup", self.outputString);

    // post settings via ajax to store in session
    /*
    var data = self.outputString;
    var postData = {
      mixitup: data,
    };
    $.ajax({
      type: 'POST',
      async: true,
      url: '/properties/settings',
      data: postData,
      //data: '{"mixitup": '+ data +'}',
      success: function(data) {
        //console.log(data);
      },
      error: function(xhr, desc, err) {
        console.log('error');
      }
    });
    */

    // Send the output string to MixItUp via the 'filter' method:
    if (self.$container.mixItUp('isLoaded')) {
      self.$container.mixItUp('filter', self.outputString);
    }
  }

};

// On document ready, initialise our code.
$(function() {

  // check if there is a url hash, and if so,
  // save it as a variable and prepend a '.' to the start - e.g. '.blue'
  // else, set variable as the default "all"
  var loadFilter = window.location.hash ? '.' + (window.location.hash).replace('#', '') : 'all';
  // If cookie exists, load settings
  //if ($.cookie('mixitup')) {
    //var loadFilter = $.cookie('mixitup');
  //}

  // Load any sort settings
  var loadSort = '';

  // Initialize buttonFilter code
  mixedFilter.init();

  // Instantiate MixItUp
  $('#js-results').mixItUp({
    controls: {
      enable: false,
      live: true,
      toggleLogic: 'and'
        //toggleFilterButtons: true,
    },
    load: {
      //filter: loadFilter,
      //filter: '.cheshire',
      sort: loadSort
    },
    callbacks: {
      onMixStart: function(state) {
        $('.mixCount').html('<i class="fa fa-cog fa-spin"></i>');

        // Show active filters
        //var filter = $('.search_type.filter');
      },
      onMixBusy: function() {
        $('.mixCount').html('<i class="fa fa-cog fa-spin"></i>');
      },
      onMixEnd: function(state) {
        // Check if any filters are running
        if (state.activeFilter == '.mix') {
          $('.js-reset').addClass('disabled').prop("disabled", true);
        } else {
          console.log('activeFilter: ' + state.activeFilter);
          $('.js-reset').removeClass('disabled').prop("disabled", false);
        }

        // Pagination stuff
        /*$('#total-page').html(state.totalPages);
        $('#current-page').html(state.activePage);
        var totalMatching = state.$targets.filter(state.activeFilter).length;
        $('.mixCount').html(totalMatching);
        pagination(state.totalPages, state.activePage);*/
      }
    },
    animation: {
      easing: 'cubic-bezier(0.86, 0, 0.07, 1)',
      duration: 600,
      queue: false
    },
    pagination: {
      //prevButtonHTML: 'Prev',
      //nextButtonHTML: 'Next',
      generatePagers: true,
      maxPagers: false,
      limit: 10,
      limit: 0
    }
  });

  // Sort Select
  $('.mixSortSelect').on('change', function() {
    $('#js-results').mixItUp('sort', this.value);
  });

  // Count the visible elements
  $('#js-results').on('mixEnd', function(e, state) {
    $('.mixCount').html(state.totalShow);
  });

  // Reset the filter fancySelects
  $('.js-reset').on('click', function() {
    $('ul.options li').removeClass('selected');
    $('ul.options li:first').addClass('selected');
    $('.search_type.filter .trigger').html('All');
  });

});
