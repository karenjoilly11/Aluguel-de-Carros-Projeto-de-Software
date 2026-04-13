import { useRef, useEffect } from 'react';
import { motion, useScroll, useTransform } from 'framer-motion';

// ── Glass stat card ──────────────────────────────────────────────
function GlassCard({ value, label, icon }) {
  return (
    <div className="hero-glass-card">
      <span className="hero-glass-icon">{icon}</span>
      <span className="hero-glass-value">{value}</span>
      <span className="hero-glass-label">{label}</span>
    </div>
  );
}

export default function HeroSection() {
  const sectionRef = useRef(null);
  const videoRef   = useRef(null);

  // Scroll progress da seção (só para efeitos de UI — NÃO afeta o vídeo)
  const { scrollYProgress } = useScroll({
    target: sectionRef,
    offset: ['start start', 'end start'],
  });

  // UI some entre 5% e 35% do scroll (só opacity — sem transforms pesados)
  const uiOpacity    = useTransform(scrollYProgress, [0, 0.05, 0.35], [1, 1, 0]);
  // Glass cards surgem entre 0% e 20%
  const glassOpacity = useTransform(scrollYProgress, [0, 0.12], [0, 1]);
  const glassY       = useTransform(scrollYProgress, [0, 0.18], [40, 0]);

  // Garante que vídeo toca ao montar
  useEffect(() => {
    const v = videoRef.current;
    if (!v) return;
    v.play().catch(() => {});
  }, []);

  return (
    <section className="hero-section" ref={sectionRef} id="hero">

      {/* ── Vídeo: sem scale animado — a GPU lida com autoplay/loop naturalmente ── */}
      <video
        ref={videoRef}
        className="hero-video"
        src="/carros/hero.webm"
        autoPlay
        loop
        muted
        playsInline
      />

      {/* Overlay escuro */}
      <div className="hero-overlay" />
      <div className="hero-gradient-bottom" />

      {/* ── UI principal — assimétrica à esquerda ── */}
      <motion.div className="hero-ui" style={{ opacity: uiOpacity }}>
        <div className="hero-ui-inner">

          <motion.span
            className="hero-badge"
            initial={{ opacity: 0, y: -16 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ duration: 0.7, delay: 0.3 }}
          >
            <span className="hero-badge-dot" />
            Frota Premium Disponível
          </motion.span>

          <motion.h1
            className="hero-title"
            initial={{ opacity: 0, y: 44 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ duration: 0.95, delay: 0.5, ease: [0.16, 1, 0.3, 1] }}
          >
            <span className="hero-title-line">Aluguel de</span>
            <span className="hero-title-accent">Alto Padrão</span>
          </motion.h1>

          <motion.p
            className="hero-subtitle"
            initial={{ opacity: 0, y: 24 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ duration: 0.9, delay: 0.7, ease: [0.16, 1, 0.3, 1] }}
          >
            Experiência de condução incomparável.
            <br />
            Máquinas de elite, entregues em 2 horas.
          </motion.p>

          <motion.div
            className="hero-ctas"
            initial={{ opacity: 0, y: 16 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ duration: 0.8, delay: 0.9 }}
          >
            <a href="#frota" className="btn-primary-hero">
              Ver Frota
              <svg width="15" height="15" viewBox="0 0 16 16" fill="none">
                <path d="M3 8h10M9 4l4 4-4 4" stroke="currentColor" strokeWidth="1.5" strokeLinecap="round" strokeLinejoin="round"/>
              </svg>
            </a>
            <a href="#sobre" className="btn-secondary-hero">
              Conheça mais
            </a>
          </motion.div>

        </div>
      </motion.div>

      {/* ── Scroll indicator ── */}
      <motion.div
        className="hero-scroll"
        initial={{ opacity: 0 }}
        animate={{ opacity: 1 }}
        transition={{ delay: 1.5, duration: 1 }}
        style={{ opacity: uiOpacity }}
      >
        <motion.div
          className="hero-scroll-dot"
          animate={{ y: [0, 8, 0] }}
          transition={{ duration: 1.5, repeat: Infinity, ease: 'easeInOut' }}
        />
      </motion.div>

      {/* ── Glass cards — surgem na base ao rolar ── */}
      <motion.div
        className="hero-glass-row"
        style={{ opacity: glassOpacity, y: glassY }}
      >
        <GlassCard value="50+"  label="Veículos"   icon="🚗" />
        <GlassCard value="98%"  label="Satisfação" icon="⭐" />
        <GlassCard value="< 2h" label="Entrega"    icon="⚡" />
        <GlassCard value="24/7" label="Suporte"    icon="🛡️" />
      </motion.div>

    </section>
  );
}
