import StatusBadge from "./StatusBadge";

export default function ResultCard({ result }) {
  if (!result) return <p>LOT 번호를 입력하세요</p>;

  return (
    <div
      style={{
        marginTop: 20,
        padding: 20,
        border: "1px solid #ddd",
        borderRadius: 10,
      }}
    >
      <StatusBadge status={result.status} />

      <h3>{result.message}</h3>

      <p>위험도: {result.dangerLevel}</p>
      <p>유효기간: {result.expirationDate}</p>
    </div>
  );
}
