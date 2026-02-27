// ==================== MOBILE MENU TOGGLE ====================
const toggle = document.getElementById("menu-toggle");
const navLinks = document.getElementById("nav-links");

toggle.addEventListener("click", (e) => {
    e.stopPropagation();
    navLinks.classList.toggle("active");
});

// Fechar menu ao clicar fora
document.addEventListener("click", (e) => {
    if (!e.target.closest(".navbar")) {
        navLinks.classList.remove("active");
    }
});

// Fechar menu ao clicar em um link
const navItems = navLinks.querySelectorAll("a");
navItems.forEach(item => {
    item.addEventListener("click", () => {
        navLinks.classList.remove("active");
    });
});

// ==================== SCROLL ANIMATIONS ====================
const reveals = document.querySelectorAll(".reveal");

const observer = new IntersectionObserver((entries) => {
    entries.forEach((entry) => {
        if (entry.isIntersecting) {
            entry.target.classList.add("active");
            observer.unobserve(entry.target);
        }
    });
}, {
    threshold: 0.2,
    rootMargin: "0px 0px -50px 0px"
});

reveals.forEach((element) => {
    observer.observe(element);
});