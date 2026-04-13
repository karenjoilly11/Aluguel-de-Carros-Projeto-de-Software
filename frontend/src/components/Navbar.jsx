import { useState, useEffect } from 'react';
import { motion, AnimatePresence } from 'framer-motion';

const NAV_LINKS = [
  { label: 'Sobre', href: '#sobre' },
  { label: 'Serviços', href: '#servicos' },
  { label: 'Frota', href: '#frota' },
  { label: 'Contato', href: '#reservar' },
];

export default function Navbar() {
  const [scrolled, setScrolled] = useState(false);
  const [menuOpen, setMenuOpen] = useState(false);

  useEffect(() => {
    const handleScroll = () => setScrolled(window.scrollY > 60);
    window.addEventListener('scroll', handleScroll, { passive: true });
    return () => window.removeEventListener('scroll', handleScroll);
  }, []);

  return (
    <motion.nav
      className={`navbar${scrolled ? ' navbar-scrolled' : ''}`}
      initial={{ y: -80, opacity: 0 }}
      animate={{ y: 0, opacity: 1 }}
      transition={{ duration: 0.7, ease: [0.16, 1, 0.3, 1] }}
    >
      <div className="navbar-inner">
        <a href="#hero" className="navbar-logo">
          <span className="navbar-logo-mark">DE</span>
          <span>DriveElite</span>
        </a>

        <ul className="navbar-links">
          {NAV_LINKS.map((link) => (
            <li key={link.href}>
              <a href={link.href} className="navbar-link">{link.label}</a>
            </li>
          ))}
        </ul>

        <a href="/login" className="navbar-cta">
          Entrar
        </a>

        <button
          className={`navbar-hamburger${menuOpen ? ' open' : ''}`}
          onClick={() => setMenuOpen(!menuOpen)}
          aria-label="Menu"
        >
          <span /><span /><span />
        </button>
      </div>

      <AnimatePresence>
        {menuOpen && (
          <motion.div
            className="navbar-mobile-menu"
            initial={{ opacity: 0, height: 0 }}
            animate={{ opacity: 1, height: 'auto' }}
            exit={{ opacity: 0, height: 0 }}
            transition={{ duration: 0.3 }}
          >
            {NAV_LINKS.map((link) => (
              <a
                key={link.href}
                href={link.href}
                className="navbar-mobile-link"
                onClick={() => setMenuOpen(false)}
              >
                {link.label}
              </a>
            ))}
            <a href="/login" className="navbar-mobile-link" onClick={() => setMenuOpen(false)}>
              Entrar
            </a>
          </motion.div>
        )}
      </AnimatePresence>
    </motion.nav>
  );
}
