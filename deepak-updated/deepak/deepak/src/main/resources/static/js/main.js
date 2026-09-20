/* =============================================
   Public portfolio behaviour.
   Deliberately dependency-free and small: no
   frontend framework is used anywhere on the site.
   ============================================= */
(function () {
    'use strict';

    var progress = document.querySelector('.scroll-progress');
    var cursorGlow = document.querySelector('.cursor-glow');
    var menuToggle = document.querySelector('.menu-toggle');
    var navLinks = document.querySelector('.nav-links');

    /* ---------- Scroll progress bar ---------- */
    if (progress) {
        var updateScrollProgress = function () {
            var scrollable = document.documentElement.scrollHeight - window.innerHeight;
            progress.style.transform =
                'scaleX(' + (scrollable > 0 ? window.scrollY / scrollable : 0) + ')';
        };
        window.addEventListener('scroll', updateScrollProgress, { passive: true });
        updateScrollProgress();
    }

    /* ---------- Cursor glow (fine pointers only) ---------- */
    if (cursorGlow && window.matchMedia('(pointer: fine)').matches) {
        window.addEventListener('pointermove', function (event) {
            cursorGlow.style.left = event.clientX + 'px';
            cursorGlow.style.top = event.clientY + 'px';
        }, { passive: true });
    }

    /* ---------- Mobile navigation ---------- */
    if (menuToggle && navLinks) {
        menuToggle.addEventListener('click', function () {
            var expanded = menuToggle.getAttribute('aria-expanded') === 'true';
            menuToggle.setAttribute('aria-expanded', String(!expanded));
            navLinks.classList.toggle('open', !expanded);
        });

        navLinks.querySelectorAll('a').forEach(function (link) {
            link.addEventListener('click', function () {
                menuToggle.setAttribute('aria-expanded', 'false');
                navLinks.classList.remove('open');
            });
        });
    }

    /* ---------- Dark / light theme ---------- */
    var STORAGE_KEY = 'portfolio-theme';
    var themeToggle = document.querySelector('.theme-toggle');

    var applyTheme = function (theme) {
        document.documentElement.setAttribute('data-theme', theme);
        if (themeToggle) {
            themeToggle.setAttribute(
                'aria-label',
                theme === 'light' ? 'Switch to dark mode' : 'Switch to light mode'
            );
        }
    };

    if (themeToggle) {
        themeToggle.addEventListener('click', function () {
            var next = document.documentElement.getAttribute('data-theme') === 'light'
                ? 'dark'
                : 'light';
            applyTheme(next);
            try {
                localStorage.setItem(STORAGE_KEY, next);
            } catch (e) {
                /* Storage can be unavailable in private mode — ignore. */
            }
        });
        applyTheme(document.documentElement.getAttribute('data-theme') || 'dark');
    }

    /* ---------- Scroll reveal ---------- */
    var revealTargets = document.querySelectorAll('.reveal');

    if ('IntersectionObserver' in window) {
        var revealObserver = new IntersectionObserver(function (entries) {
            entries.forEach(function (entry) {
                if (entry.isIntersecting) {
                    entry.target.classList.add('visible');
                    revealObserver.unobserve(entry.target);
                }
            });
        }, { threshold: 0.12 });

        revealTargets.forEach(function (element) {
            revealObserver.observe(element);
        });
    } else {
        revealTargets.forEach(function (element) {
            element.classList.add('visible');
        });
    }

    /* ---------- Back to top utility ---------- */
    var backTop = document.querySelector('.back-to-top-float');
    if (backTop) {
        var updateBackTop = function () {
            backTop.classList.toggle('visible', window.scrollY > 650);
        };
        window.addEventListener('scroll', updateBackTop, { passive: true });
        updateBackTop();
        backTop.addEventListener('click', function () {
            window.scrollTo({ top: 0, behavior: 'smooth' });
        });
    }

    /* ---------- Contact popup (toast) ---------- */
    var toasts = document.querySelectorAll('[data-toast]');

    if (toasts.length) {
        var reduceMotion = window.matchMedia &&
            window.matchMedia('(prefers-reduced-motion: reduce)').matches;

        var dismissToast = function (toast) {
            if (!toast || toast.classList.contains('is-leaving')) {
                return;
            }
            toast.classList.add('is-leaving');
            // Timeout fallback: with reduced motion no animation event fires.
            window.setTimeout(function () {
                if (toast.parentNode) {
                    toast.parentNode.removeChild(toast);
                }
            }, 320);
        };

        toasts.forEach(function (toast) {
            var closeButton = toast.querySelector('[data-toast-close]');
            var timerBar = toast.querySelector('.toast-timer');

            if (closeButton) {
                closeButton.addEventListener('click', function () {
                    dismissToast(toast);
                });
            }

            if (timerBar && !reduceMotion) {
                // The CSS countdown bar drives the auto-dismiss, so hovering
                // the toast (which pauses the bar) also pauses the timeout.
                timerBar.addEventListener('animationend', function () {
                    dismissToast(toast);
                });
            } else {
                window.setTimeout(function () {
                    dismissToast(toast);
                }, toast.classList.contains('toast-error') ? 8000 : 6000);
            }

            // A rejected form: put the cursor in the first invalid field.
            if (toast.classList.contains('toast-error')) {
                var firstInvalid = document.querySelector('.contact .is-invalid');
                if (firstInvalid) {
                    try {
                        firstInvalid.focus({ preventScroll: true });
                    } catch (e) {
                        firstInvalid.focus();
                    }
                }
            }
        });

        document.addEventListener('keydown', function (event) {
            if (event.key === 'Escape') {
                document.querySelectorAll('[data-toast]').forEach(dismissToast);
            }
        });
    }

    /* ---------- Active section in the navbar ---------- */
    var sections = document.querySelectorAll('main section[id]');
    var navAnchors = document.querySelectorAll('.nav-links a[href^="#"]');

    if (sections.length && navAnchors.length && 'IntersectionObserver' in window) {
        var sectionObserver = new IntersectionObserver(function (entries) {
            entries.forEach(function (entry) {
                if (!entry.isIntersecting) {
                    return;
                }
                navAnchors.forEach(function (anchor) {
                    anchor.classList.toggle(
                        'active',
                        anchor.getAttribute('href') === '#' + entry.target.id
                    );
                });
            });
        }, { rootMargin: '-45% 0px -50% 0px' });

        sections.forEach(function (section) {
            sectionObserver.observe(section);
        });
    }
}());
