document.addEventListener('DOMContentLoaded', function () {

    var toggles = document.querySelectorAll('.password-toggle');

    toggles.forEach(function (btn) {
        btn.addEventListener('click', function () {
            var targetId = btn.getAttribute('data-target');
            var input = document.getElementById(targetId);
            if (!input) return;

            var icon = btn.querySelector('i');
            var isHidden = input.type === 'password';

            input.type = isHidden ? 'text' : 'password';

            if (icon) {
                icon.classList.toggle('bx-show', !isHidden);
                icon.classList.toggle('bx-hide', isHidden);
            }
        });
    });

});