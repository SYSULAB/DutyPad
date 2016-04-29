$(document).ready(function() {

	$('div.ui.tabular.menu > a.item').click(function() {
		var menu = $(this).parent('.menu');
		menu.children('a.item.active').removeClass('active');
		$(this).addClass('active');
	})

	$("#newDutyModal").modal({
			blurring: true
		})
		.modal('setting', 'transition', 'horizontal flip');

	$("#setBeginDayModal").modal({
			blurring: true
		})
		.modal('setting', 'transition', 'horizontal flip');

	$("#fixTaskModal").modal({
			blurring: true
		})
		.modal('setting', 'transition', 'horizontal flip');

	$("#setNewRecord").modal({
			blurring: true
		})
		.modal('setting', 'transition', 'horizontal flip');

	$("#setNewDeadTable").modal({
			blurring: true
		})
		.modal('setting', 'transition', 'horizontal flip');

	$('.ui.labeled.icon.sidebar')
		.not('.styled')
		.sidebar('setting', {
			dimPage: true,
			transition: 'overlay',
			mobileTransition: 'overlay'
		});
	$('.small.ui.progress')
		.progress({
			label: 'ratio',
			text: {
				ratio: '{value}'
			}
		});
	
	$('.ui.search.dropdown')
		.dropdown();

	$('.ui.dropdown')
		.dropdown();

	$(".popup").popup();
	$(".popup-hover").popup({
		hoverable : true
	});
	
	$("body").fadeIn();
});
