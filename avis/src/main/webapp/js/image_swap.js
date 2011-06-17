// Swaps images by animating the "left" property of the DOM element.
// Enrique Delgado, 12/4/2008

var slideshow = true; // Set to false if you want to disable the automated slideshow.
var slideshowIterval = 5000; // In miliseconds
var scrollInterval;
var current = 1;

$(document).ready(function(){
    // Keeps the current image index
    //var current = 1;
    // Control flag:
    var userInControl = false;

    // Calculate the with of the viewport div
    var w = $(".page-ad-cover")[0].clientWidth;

    // ScrollTo the first image, and highlight it's number
    scrollTo(current);

    // Sets an event listener when an individual page number is clicked:
    $('.page-ad-li').click(function(e){
        /* Determine the next image index using the href attribute as value
           Expects this markup structure:
           <ul>
               <li class = "page-ad-li"><a class="page-ad-a" href="n">n</a></li>
           </ul>
        */
        // Disable the slideshow.. the user is now in control
        clearInterval(scrollInterval);
	userInControl = true;
        // Get the number of the button:
        button = $($(this).children()[0]).attr('href');
        // Obtain the next slide, rotating if necessary:
        current = calcNext(button);
        // Move the image index using the href attribute as value
        scrollTo(current);
        // Ignore the click on me
        e.preventDefault();
    });

    // Function to show next slide
    function rotateImage() {
        // Obtain the next slide, rotating if necessary:
        current = calcNext(current + 1);
        // Move the image index using the href attribute as value
        scrollTo(current);
    }

    // Calculates next slide, given a slice number, rotates as necessary:
    function calcNext(index) {
        if (index < 1) {
            if (current > 1) {
                current -= 1;
            } else {
                current = total;
            }
        } else if (index > total) {
                if (current < total) {
                current += 1;
            } else {
                current = 1;
            }
        } else {
            current = index;
        }
        return current;
    }

    // Here is where the magic happens, animate the slides:
    function scrollTo(index) {
        var thisLi = $(".page-ad-a[href="+ index +"]").parent();    // Find the current LI item
        thisLi.addClass('active');                                  // Make this LI show as active
        thisLi.siblings().removeClass('active');                    // Make all other neighbor LIs show as inactive
        var l = ( index - 1 ) * w;                                  // Determine what the new "left" offset should be
        $('.page-ad-strip').animate({left : "-" + l + "px"});       // Move to that value
    };

    // Setup slideshow
    if (slideshow) {
        scrollInterval = setInterval(rotateImage,slideshowIterval); //time in milliseconds
        $('.page-ad-strip').hover(function() {
            clearInterval(scrollInterval);
        }, function() {
            if (!userInControl) {
                scrollInterval = setInterval(rotateImage,slideshowIterval); //time in milliseconds
            }
            //rotateImage();
        });
    }

});
