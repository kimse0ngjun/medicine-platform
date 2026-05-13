import StatusBadge from "./StatusBadge";

export default function ResultCard({ result }) {
  if (!result) return <p>LOT 번호를 입력하세요</p>;

  return (
    <div className="result-card">
      <StatusBadge status={result?.status ?? "unknown"} />

      <h3 className="result-card__title">{result.message}</h3>

      <div className="result-card__info">
        <p>위험도: {result.dangerLevel}</p>
        <p>
          유효기간:{" "}
          {result.expirationDate
            ? new Date(result.expirationDate).toLocaleDateString()
            : "-"}
        </p>
      </div>
    </div>
  );
}
