export default function StatusBadge({ level }) {
  return <span className={`status-badge status-badge--${level}`}>{level}</span>;
}
