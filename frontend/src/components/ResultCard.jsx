import StatusBadge from "./StatusBadge";

export default function ResultCard({ result }) {
  return (
    <div className="result-card">
      {result.productName && (
        <div className="result-row">
          <span>제품명:</span>
          <strong>{result.productName}</strong>
        </div>
      )}

      {result.dangerLevel && (
        <div className="result-row">
          <span>위험도:</span>
          <strong>{result.dangerLevel}</strong>
        </div>
      )}

      {result.expirationDate && (
        <div className="result-row">
          <span>유효기간:</span>
          <strong>{result.expirationDate}</strong>
        </div>
      )}
    </div>
  );
}
