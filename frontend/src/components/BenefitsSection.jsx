import { motion } from 'framer-motion';

const BENEFITS = [
  {
    icon: (
      <svg width="28" height="28" viewBox="0 0 28 28" fill="none">
        <path d="M14 3L3 9v10l11 6 11-6V9L14 3z" stroke="#2B4CFF" strokeWidth="1.5" strokeLinejoin="round"/>
        <path d="M14 3v16M3 9l11 6 11-6" stroke="#2B4CFF" strokeWidth="1.5" strokeLinejoin="round"/>
      </svg>
    ),
    title: 'Frota Premium',
    desc: 'Veículos de alto padrão, revisados e prontos para entregar a máxima performance.',
  },
  {
    icon: (
      <svg width="28" height="28" viewBox="0 0 28 28" fill="none">
        <circle cx="14" cy="14" r="10" stroke="#2B4CFF" strokeWidth="1.5"/>
        <path d="M14 8v6l4 2" stroke="#2B4CFF" strokeWidth="1.5" strokeLinecap="round"/>
      </svg>
    ),
    title: 'Disponível 24/7',
    desc: 'Suporte e atendimento ininterrupto para que você nunca fique sem assistência.',
  },
  {
    icon: (
      <svg width="28" height="28" viewBox="0 0 28 28" fill="none">
        <path d="M5 14h18M14 5l9 9-9 9" stroke="#2B4CFF" strokeWidth="1.5" strokeLinecap="round" strokeLinejoin="round"/>
      </svg>
    ),
    title: 'Entrega Expressa',
    desc: 'O veículo vai até você. Entrega no endereço escolhido em até 2 horas.',
  },
  {
    icon: (
      <svg width="28" height="28" viewBox="0 0 28 28" fill="none">
        <path d="M14 3l3 6 7 1-5 5 1 7-6-3-6 3 1-7-5-5 7-1 3-6z" stroke="#2B4CFF" strokeWidth="1.5" strokeLinejoin="round"/>
      </svg>
    ),
    title: 'Seguro Incluso',
    desc: 'Cobertura total inclusa em todos os planos. Dirija com tranquilidade e segurança.',
  },
  {
    icon: (
      <svg width="28" height="28" viewBox="0 0 28 28" fill="none">
        <rect x="4" y="8" width="20" height="14" rx="2" stroke="#2B4CFF" strokeWidth="1.5"/>
        <path d="M4 12h20M9 17h2M15 17h4" stroke="#2B4CFF" strokeWidth="1.5" strokeLinecap="round"/>
      </svg>
    ),
    title: 'Sem Burocracia',
    desc: 'Processo 100% digital. Reserve em minutos, sem filas ou papelada.',
  },
  {
    icon: (
      <svg width="28" height="28" viewBox="0 0 28 28" fill="none">
        <path d="M14 4C8.48 4 4 8.48 4 14s4.48 10 10 10 10-4.48 10-10S19.52 4 14 4z" stroke="#2B4CFF" strokeWidth="1.5"/>
        <path d="M9 14l3 3 7-7" stroke="#2B4CFF" strokeWidth="1.5" strokeLinecap="round" strokeLinejoin="round"/>
      </svg>
    ),
    title: 'Garantia de Qualidade',
    desc: 'Inspeção rigorosa antes de cada locação. Zero surpresas, máxima confiança.',
  },
];

export default function BenefitsSection() {
  return (
    <section className="benefits-section" id="beneficios">
      {/* Background grid decorativo */}
      <div className="benefits-bg-grid" aria-hidden="true" />

      <div className="section-container">
        <motion.div
          className="section-header"
          initial={{ opacity: 0, y: 30 }}
          whileInView={{ opacity: 1, y: 0 }}
          viewport={{ once: true }}
          transition={{ duration: 0.7 }}
        >
          <span className="section-tag">Por que escolher</span>
          <h2 className="section-title">
            Diferenciais que <span className="accent-text">fazem a diferença</span>
          </h2>
        </motion.div>

        <div className="benefits-grid">
          {BENEFITS.map((b, i) => (
            <motion.div
              key={b.title}
              className="benefit-card"
              initial={{ opacity: 0, y: 40 }}
              whileInView={{ opacity: 1, y: 0 }}
              viewport={{ once: true, margin: '-60px' }}
              transition={{ duration: 0.6, delay: i * 0.08, ease: [0.16, 1, 0.3, 1] }}
              whileHover={{ y: -6, transition: { duration: 0.2 } }}
            >
              <div className="benefit-icon">{b.icon}</div>
              <h3 className="benefit-title">{b.title}</h3>
              <p className="benefit-desc">{b.desc}</p>
            </motion.div>
          ))}
        </div>
      </div>
    </section>
  );
}
